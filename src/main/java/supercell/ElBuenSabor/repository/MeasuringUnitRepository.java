package supercell.ElBuenSabor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import supercell.ElBuenSabor.Models.MeasuringUnit;
@Repository
public interface MeasuringUnitRepository extends JpaRepository<MeasuringUnit, Long> {
}