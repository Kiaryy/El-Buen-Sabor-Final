package supercell.ElBuenSabor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import supercell.ElBuenSabor.Models.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.client.id = :userId")
    List<Order> findOrderByClient(@Param("userId") Long userId);

    @Query("""
           SELECT DISTINCT o FROM Order o
           LEFT JOIN FETCH o.orderDetails od
           LEFT JOIN FETCH od.article a
           LEFT JOIN FETCH od.manufacturedArticle ma
           LEFT JOIN FETCH od.sale s
           LEFT JOIN FETCH s.saleDetails sd
           LEFT JOIN FETCH sd.article sa
           LEFT JOIN FETCH sd.manufacturedArticle sma
           """)
    List<Order> findAllWithDetails();
}
