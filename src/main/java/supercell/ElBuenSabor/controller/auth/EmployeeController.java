package supercell.ElBuenSabor.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supercell.ElBuenSabor.Models.Employee;
import supercell.ElBuenSabor.Models.payload.EmployeeDto;
import supercell.ElBuenSabor.Models.payload.UserLoginDTO;
import supercell.ElBuenSabor.service.AuthService;
import supercell.ElBuenSabor.service.impl.EmployeeServiceImpl;

import java.util.List;


@RestController
@RequestMapping("employee")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EmployeeController {

    private final AuthService<EmployeeDto> authService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Employee>> getAll(){
        EmployeeServiceImpl service = (EmployeeServiceImpl) authService;
        return ResponseEntity.ok(service.getAllEmployees());
    }
    
    @PostMapping("/register")
    public ResponseEntity<EmployeeDto> registerEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto response =  authService.register(employeeDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        String response = authService.logIn(userLoginDTO.email(), userLoginDTO.password());
        return ResponseEntity.ok(response);

    }
}

