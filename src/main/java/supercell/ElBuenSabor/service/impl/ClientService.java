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
    public String logIn(String username, String password) {
        Optional<Client> clientOpt = clientRepository.findByUsernameAndPassword(username, password);
        return clientOpt.isPresent() ? "Login OK" : "Login failed";
    }

    @Override
    public ClientDto register(ClientDto dto) {
        Client client = new Client();
        client.setName(dto.getFirstName());
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
