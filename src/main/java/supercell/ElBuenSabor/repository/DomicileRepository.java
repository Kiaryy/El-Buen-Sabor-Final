package supercell.ElBuenSabor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import supercell.ElBuenSabor.Models.Domicile;
@Repository
public interface DomicileRepository extends JpaRepository<Domicile, Long> {
}
