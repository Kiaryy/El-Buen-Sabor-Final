package supercell.ElBuenSabor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import supercell.ElBuenSabor.Models.Province;
@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long>{
    
}
