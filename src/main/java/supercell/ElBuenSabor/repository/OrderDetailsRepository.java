package supercell.ElBuenSabor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import supercell.ElBuenSabor.Models.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {}

