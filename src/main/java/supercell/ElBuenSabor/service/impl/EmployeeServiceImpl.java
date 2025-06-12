package supercell.ElBuenSabor.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import supercell.ElBuenSabor.Models.Domicile;
import supercell.ElBuenSabor.Models.Employee;
import supercell.ElBuenSabor.Models.Location;
import supercell.ElBuenSabor.Models.payload.DomicileDTO;
import supercell.ElBuenSabor.Models.payload.EmployeeDto;
import supercell.ElBuenSabor.repository.EmployeeRepository;
import supercell.ElBuenSabor.repository.LocationRepository;
import supercell.ElBuenSabor.service.AuthService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements AuthService<EmployeeDto> {

    @Autowired
    private final EmployeeRepository employeeRepository;
    @Autowired
    private final LocationRepository locationRepository;

    @Override
    public String logIn(String email, String password) {
        String response;
        Optional<Employee> emp = employeeRepository.findByEmailAndPassword(email, password);
        if (emp.isPresent()) {
            response = "Login OK, Rol: " + emp.get().getEmployeeRole();
        } else{
            response = "Login Failed";
        }
        return response ;
    }

    
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    @Override
    public EmployeeDto register(EmployeeDto dto) {
        employeeRepository.findByEmail(dto.getEmail()).ifPresent(existing ->{
            throw new IllegalArgumentException("Email already in use");
        });

        Employee emp = new Employee();
        emp.setName(dto.getFirstName());
        emp.setLastName(dto.getLastName());
        emp.setPhoneNumber(dto.getPhoneNumber());
        emp.setEmail(dto.getEmail());
        emp.setBirthDate(dto.getBirthDate());
        emp.setUsername(dto.getUsername());
        emp.setPassword(dto.getPassword());
        emp.setEmployeeRole(dto.getRole());
        emp.setSalary(dto.getSalary());
        emp.setShift(dto.getShift());

        List<Domicile> domiciles = new ArrayList<>();
                for (DomicileDTO domicile : dto.getDomiciles()) {
            Location location = locationRepository.findById(domicile.location()).
                orElseThrow(() -> new EntityNotFoundException("Location no encontrado con ID: " + domicile.location()));
            Domicile newDomicile = Domicile.builder()
                .street(domicile.street())
                .zipCode(domicile.zipcode())
                .number(domicile.number())
                .location(location)
                .employee(emp)
                .build();
            
            domiciles.add(newDomicile);
        }
        emp.setDomiciles(domiciles);
        employeeRepository.save(emp);
        return dto;
    }
}
