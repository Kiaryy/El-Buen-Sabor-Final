package supercell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import supercell.Models.Domicile;

public interface DomicileRepository extends JpaRepository<Domicile, Long> {
}
