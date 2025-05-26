package supercell.ElBuenSabor.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supercell.ElBuenSabor.Models.payload.EmployeeDto;
import supercell.ElBuenSabor.service.AuthService;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final AuthService<EmployeeDto> authService;

    @PostMapping("/register")
    public ResponseEntity<EmployeeDto> registerEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto response =  authService.register(employeeDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        String response = authService.logIn(username, password);
        return ResponseEntity.ok(response);

    }
}

