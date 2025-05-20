package supercell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import supercell.Models.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
