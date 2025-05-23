package supercell.ElBuenSabor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import supercell.ElBuenSabor.Models.Subsidiary;

@Repository
public interface SubsidiaryRepository extends JpaRepository<Subsidiary, Long> {
}
