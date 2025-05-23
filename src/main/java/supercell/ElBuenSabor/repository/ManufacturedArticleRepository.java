package supercell.ElBuenSabor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import supercell.ElBuenSabor.Models.ManufacturedArticle;

@Repository
public interface ManufacturedArticleRepository extends JpaRepository<ManufacturedArticle, Long> {
}
