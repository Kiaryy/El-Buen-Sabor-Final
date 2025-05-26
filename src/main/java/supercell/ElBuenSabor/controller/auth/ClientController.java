package supercell.ElBuenSabor.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supercell.ElBuenSabor.Models.payload.ClientDto;
import supercell.ElBuenSabor.service.AuthService;
import supercell.ElBuenSabor.service.impl.ClientService;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final AuthService<ClientDto> clientAuthService;

    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestBody ClientDto clientDto) {
        ClientDto registered = clientAuthService.register(clientDto);
        return ResponseEntity.ok("Client registered successfully");
    }

    @PostMapping("/login/userName/{username}/password/{password}")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        String response = clientAuthService.logIn(username, password);
        return ResponseEntity.ok(response);
    }
}
