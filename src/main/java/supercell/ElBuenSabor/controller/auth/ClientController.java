package supercell.ElBuenSabor.controller.auth;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import supercell.ElBuenSabor.Models.Client;
import supercell.ElBuenSabor.Models.payload.ClientDto;
import supercell.ElBuenSabor.Models.payload.UserLoginDTO;
import supercell.ElBuenSabor.service.AuthService;
import supercell.ElBuenSabor.service.impl.ClientService;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ClientController {

    private final AuthService<ClientDto> clientAuthService;


    @GetMapping("/ID/{ID}")
    public ResponseEntity<Object> returnClient(@PathVariable("ID") Long ID){
        ClientService service = (ClientService) clientAuthService;
        return ResponseEntity.status(HttpStatus.OK).body(service.getClient(ID));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Object> returnClientWithEmail(@PathVariable("email") String email){
        ClientService service = (ClientService) clientAuthService;
        return ResponseEntity.status(HttpStatus.OK).body(service.getClientWithEmail(email));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestBody ClientDto clientDto) {
        ClientDto registered = clientAuthService.register(clientDto);
        return ResponseEntity.ok("Client registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO clientLoginDTO) {
        String response = clientAuthService.logIn(clientLoginDTO.email(), clientLoginDTO.password());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> patchClient(@PathVariable Long id, @RequestBody ClientDto dto) {
        ClientService service = (ClientService) clientAuthService;
        Client updated = service.updateClient(id, dto);
        return ResponseEntity.ok(updated);
    }
}
