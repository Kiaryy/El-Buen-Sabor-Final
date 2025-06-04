package supercell.ElBuenSabor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import supercell.ElBuenSabor.Models.Country;
@Repository
public interface CountryRepository extends JpaRepository<Country, Long>{
    
}
