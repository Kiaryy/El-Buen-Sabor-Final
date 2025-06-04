package supercell.ElBuenSabor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import supercell.ElBuenSabor.Models.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmailAndPassword(String email, String password);
    Optional<Employee> findByEmail(String email);
}
