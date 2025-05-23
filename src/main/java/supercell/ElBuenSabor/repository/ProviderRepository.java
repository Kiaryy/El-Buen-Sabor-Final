package supercell.ElBuenSabor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import supercell.ElBuenSabor.Models.Provider;

@Repository
public interface ProviderRepository  extends JpaRepository<Provider, Long>{
    
}
