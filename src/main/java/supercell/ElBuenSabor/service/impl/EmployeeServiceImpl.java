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
        if (emp.isPresent() && emp.get().isEnabled()) {
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
        emp.setEnabled(dto.isEnabled());

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

    public Employee updateEmployee(Long ID, EmployeeDto employeeDto) {
        return employeeRepository.findById(ID).map(existingEmployee -> {
            if (employeeDto.getFirstName() != null) {
                existingEmployee.setName(employeeDto.getFirstName());
            }
            if (employeeDto.getLastName() != null) {
                existingEmployee.setLastName(employeeDto.getLastName());
            }
            if (employeeDto.getPhoneNumber() != null) {
                existingEmployee.setPhoneNumber(employeeDto.getPhoneNumber());
            }
            if (employeeDto.getEmail() != null) {
                existingEmployee.setEmail(employeeDto.getEmail());
            }
            if (employeeDto.getBirthDate() != null) {
                existingEmployee.setBirthDate(employeeDto.getBirthDate());
            }
            if (employeeDto.getUsername() != null) {
                existingEmployee.setUsername(employeeDto.getUsername());
            }
            if (employeeDto.getPassword() != null) {
                existingEmployee.setPassword(employeeDto.getPassword());
            }
            if (employeeDto.getRole() != null) {
                existingEmployee.setEmployeeRole(employeeDto.getRole());
            }
            if (employeeDto.getSalary() != null) {
                existingEmployee.setSalary(employeeDto.getSalary());
            }
            if (employeeDto.getShift() != null) {
                existingEmployee.setShift(employeeDto.getShift());
            }
    
            // Enabled is a primitive boolean, so always update
            existingEmployee.setEnabled(employeeDto.isEnabled());
    
            // Domiciles update (optional: replace old domiciles)
            if (employeeDto.getDomiciles() != null && !employeeDto.getDomiciles().isEmpty()) {
                List<Domicile> domiciles = new ArrayList<>();
                for (DomicileDTO domicile : employeeDto.getDomiciles()) {
                    Location location = locationRepository.findById(domicile.location())
                            .orElseThrow(() -> new EntityNotFoundException("Location no encontrado con ID: " + domicile.location()));
    
                    Domicile newDomicile = Domicile.builder()
                            .street(domicile.street())
                            .zipCode(domicile.zipcode())
                            .number(domicile.number())
                            .location(location)
                            .employee(existingEmployee)
                            .build();
    
                    domiciles.add(newDomicile);
                }
                existingEmployee.setDomiciles(domiciles);
            }
    
            return employeeRepository.save(existingEmployee);
        }).orElseThrow(() -> new EntityNotFoundException("No se encontró un empleado con el ID: " + ID));
    }
    
}
