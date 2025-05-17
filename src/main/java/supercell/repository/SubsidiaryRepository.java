package supercell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import supercell.Models.Subsidiary;

public interface SubsidiaryRepository extends JpaRepository<Subsidiary, Long> {
}
