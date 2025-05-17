package supercell.service;

import supercell.Models.Article;
import supercell.Models.Category;

import java.util.List;

public interface CategoryService {

    public List<Category> getAllCategories();
    public Category updateCategory(Category article);
    public Category addCategory(Category article);
    public Category deleteCategory(Category article);
}
