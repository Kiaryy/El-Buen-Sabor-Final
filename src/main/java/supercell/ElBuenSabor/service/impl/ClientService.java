package supercell.ElBuenSabor.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import supercell.ElBuenSabor.Models.Client;
import supercell.ElBuenSabor.Models.payload.ClientDto;
import supercell.ElBuenSabor.repository.ClientRepository;
import supercell.ElBuenSabor.service.AuthService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService implements AuthService<ClientDto> {

    private final ClientRepository clientRepository;

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

    
    public Client getClient(Long ID){
        Client client = clientRepository.findById(ID).
        orElseThrow(() -> new EntityNotFoundException("No se encontro el cliente con el ID: " + ID));
        return client;
    }
    
    @Override
    public ClientDto register(ClientDto dto) {
        Client client = new Client();
        client.setUsername(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setPhoneNumber(String.valueOf(dto.getPhoneNumber()));
        client.setEmail(dto.getEmail());
        client.setBirthDate(dto.getBirthDate());
        client.setUsername(dto.getUsername());
        client.setPassword(dto.getPassword());

        clientRepository.save(client);
        return dto;
    }
}
