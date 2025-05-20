package supercell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import supercell.Models.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
