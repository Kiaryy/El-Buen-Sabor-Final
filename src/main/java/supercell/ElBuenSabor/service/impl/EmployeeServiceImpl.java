package supercell.ElBuenSabor.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import supercell.ElBuenSabor.Models.Employee;
import supercell.ElBuenSabor.Models.payload.EmployeeDto;
import supercell.ElBuenSabor.repository.EmployeeRepository;
import supercell.ElBuenSabor.service.AuthService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements AuthService<EmployeeDto> {

    private final EmployeeRepository employeeRepository;

    @Override
    public String logIn(String username, String password) {
        Optional<Employee> emp = employeeRepository.findByUsernameAndPassword(username, password);
        return emp.isPresent() ? "Login OK" : "Login failed";
    }

    @Override
    public EmployeeDto register(EmployeeDto dto) {
        Employee emp = new Employee();
        emp.setName(dto.getFirstName());
        emp.setLastName(dto.getLastName());
        emp.setPhoneNumber(String.valueOf(dto.getPhoneNumber()));
        emp.setEmail(dto.getEmail());
        emp.setBirthDate(dto.getBirthDate());
        
        emp.setUsername(dto.getUsername());

        emp.setPassword(dto.getPassword());
        emp.setEmployeeRole(dto.getRole());
        emp.setSalary(dto.getSalary());
        emp.setShift(dto.getShift());

        employeeRepository.save(emp);
        return dto;
    }
}
