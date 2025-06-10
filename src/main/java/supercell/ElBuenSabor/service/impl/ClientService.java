package supercell.ElBuenSabor.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import supercell.ElBuenSabor.Models.Client;
import supercell.ElBuenSabor.Models.Domicile;
import supercell.ElBuenSabor.Models.Location;
import supercell.ElBuenSabor.Models.payload.ClientDto;
import supercell.ElBuenSabor.Models.payload.DomicileDTO;
import supercell.ElBuenSabor.repository.ClientRepository;
import supercell.ElBuenSabor.repository.LocationRepository;
import supercell.ElBuenSabor.service.AuthService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService implements AuthService<ClientDto> {

    @Autowired
    private final ClientRepository clientRepository;
    @Autowired
    private final LocationRepository locationRepository;

    @Override
    public String logIn(String email, String password) {
        Optional<Client> clientOpt = clientRepository.findByEmailAndPassword(email, password);
        return clientOpt.isPresent() ? "Login OK" : "Login failed";
    }

    
    public Client getClient(Long ID){
        Client client = clientRepository.findById(ID).
        orElseThrow(() -> new EntityNotFoundException("No se encontro el cliente con el ID: " + ID));
        return client;
    }
    
    @Override
    public ClientDto register(ClientDto dto) {
        clientRepository.findByEmail(dto.getEmail()).ifPresent(existing -> {
            throw new IllegalArgumentException("Email already in use");
        });


        Client client = new Client();
        client.setName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setEmail(dto.getEmail());
        client.setBirthDate(dto.getBirthDate());
        client.setUsername(dto.getUsername());
        client.setPassword(dto.getPassword());

        List<Domicile> domiciles = new ArrayList<>();
        for (DomicileDTO domicile : dto.getDomiciles()) {
            Location location = locationRepository.findById(domicile.location()).
                orElseThrow(() -> new EntityNotFoundException("Location no encontrado con ID: " + domicile.location()));
            Domicile newDomicile = Domicile.builder()
                .street(domicile.street())
                .zipCode(domicile.zipcode())
                .number(domicile.number())
                .location(location)
                .client(client)
                .build();
            
            domiciles.add(newDomicile);
        }
        client.setDomiciles(domiciles);
        clientRepository.save(client);
        return dto;
    }
}
