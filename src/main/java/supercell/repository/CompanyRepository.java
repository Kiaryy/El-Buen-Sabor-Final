package supercell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import supercell.Models.Company;

public interface CompanyRepository extends JpaRepository<Company,Long> {
}
