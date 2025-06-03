package supercell.ElBuenSabor.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supercell.ElBuenSabor.Models.payload.EmployeeDto;
import supercell.ElBuenSabor.Models.payload.UserLoginDTO;
import supercell.ElBuenSabor.service.AuthService;

@RestController
@RequestMapping("employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final AuthService<EmployeeDto> authService;

    @PostMapping("/register")
    public ResponseEntity<EmployeeDto> registerEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto response =  authService.register(employeeDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        String response = authService.logIn(userLoginDTO.username(), userLoginDTO.password());
        return ResponseEntity.ok(response);

    }
}

