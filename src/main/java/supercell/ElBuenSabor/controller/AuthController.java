package supercell.ElBuenSabor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import supercell.ElBuenSabor.Models.payload.NewUser;
import supercell.ElBuenSabor.Models.payload.UserLogin;
import supercell.ElBuenSabor.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  /*  private final AuthService authService;

    @PostMapping("/user/logIn")
    public ResponseEntity<String>logIn(@RequestBody String username, @RequestBody String password) {
      String response = authService.logIn(username, password);
      return ResponseEntity.ok(response);
    }

    @PostMapping("/user/register")
    public ResponseEntity<NewUser>logUp(NewUser user) {
        NewUser newUser = authService.register(user);
        return ResponseEntity.ok(newUser);
    }*/
}
