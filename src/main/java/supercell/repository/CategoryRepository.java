package supercell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import supercell.Models.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
