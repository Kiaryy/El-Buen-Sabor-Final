package supercell.ElBuenSabor.service;

import java.util.List;

import supercell.ElBuenSabor.Models.Category;
import supercell.ElBuenSabor.Models.payload.CategoryDTO;

public interface CategoryService {

    public List<Category> getAllCategories();
    public Category updateCategory(Long ID, CategoryDTO categoryDTO);
    public Category addCategory(CategoryDTO categoryDTO);
}
