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
        String response;
        Optional<Client> clientOpt = clientRepository.findByEmailAndPassword(email, password);
        if(clientOpt.isPresent()){
            response = "Login OK, ID: " + clientOpt.get().getId();
        } else{
            response = "Login failed";
        }
        return response;
    }

    public Client getClientWithEmail(String email){
        Client client = clientRepository.findByEmail(email).
        orElseThrow(()-> new EntityNotFoundException("No se encontro el cliente con email: " + email));
        return client;
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

    public Client updateClient(Long id, ClientDto dto) {
        Client client = clientRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + id));
    
        if (dto.getFirstName() != null) client.setName(dto.getFirstName());
        if (dto.getLastName() != null) client.setLastName(dto.getLastName());
        if (dto.getEmail() != null) client.setEmail(dto.getEmail());
        if (dto.getPhoneNumber() != null) client.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getUsername() != null) client.setUsername(dto.getUsername());
        if (dto.getPassword() != null) client.setPassword(dto.getPassword());
        if (dto.getBirthDate() != null) client.setBirthDate(dto.getBirthDate());
    
        if (dto.getDomiciles() != null) {
            client.getDomiciles().clear();
    
            for (DomicileDTO domicileDto : dto.getDomiciles()) {
                Location location = locationRepository.findById(domicileDto.location())
                    .orElseThrow(() -> new EntityNotFoundException("Location not found with ID: " + domicileDto.location()));
    
                Domicile domicile = Domicile.builder()
                    .street(domicileDto.street())
                    .zipCode(domicileDto.zipcode())
                    .number(domicileDto.number())
                    .location(location)
                    .client(client)
                    .build();
    
                client.getDomiciles().add(domicile);
            }
        }
    
        return clientRepository.save(client);
    }
}


