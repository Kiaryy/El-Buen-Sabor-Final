package supercell.ElBuenSabor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import supercell.ElBuenSabor.Models.InventoryImage;

@Repository
public interface InventoryImageRepository extends JpaRepository<InventoryImage, Long>{
    
}
