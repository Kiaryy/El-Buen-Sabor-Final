package supercell.ElBuenSabor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import supercell.ElBuenSabor.Models.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {}
