package supercell.ElBuenSabor.controller.auth;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import supercell.ElBuenSabor.Models.payload.ClientDto;
import supercell.ElBuenSabor.Models.payload.UserLoginDTO;
import supercell.ElBuenSabor.service.AuthService;
import supercell.ElBuenSabor.service.impl.ClientService;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final AuthService<ClientDto> clientAuthService;


    @GetMapping("/{ID}")
    public ResponseEntity<Object> returnClient(@PathVariable Long ID){
        ClientService service = (ClientService) clientAuthService;
        return ResponseEntity.status(HttpStatus.OK).body(service.getClient(ID));
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
}
