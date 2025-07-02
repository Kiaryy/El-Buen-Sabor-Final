package supercell.ElBuenSabor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import supercell.ElBuenSabor.Models.ManufacturedArticle;
import supercell.ElBuenSabor.Models.Sale;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("""
    SELECT DISTINCT s
    FROM Sale s
    JOIN s.saleDetails sd
    WHERE sd.manufacturedArticle IN :articles
    """)
    List<Sale> findSalesByManufacturedArticles(@Param("articles") List<ManufacturedArticle> articles);

}
