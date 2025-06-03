package supercell.ElBuenSabor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import supercell.ElBuenSabor.Models.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUsernameAndPassword(String username, String password);
}
