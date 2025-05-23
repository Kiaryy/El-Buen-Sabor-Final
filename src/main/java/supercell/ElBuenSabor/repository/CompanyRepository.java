package supercell.ElBuenSabor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import supercell.ElBuenSabor.Models.Company;
@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
}
