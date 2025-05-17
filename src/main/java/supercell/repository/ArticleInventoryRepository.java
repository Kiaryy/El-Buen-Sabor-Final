package supercell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import supercell.Models.ArticleInventory;

public interface ArticleInventoryRepository extends JpaRepository<ArticleInventory, Long> {
}
