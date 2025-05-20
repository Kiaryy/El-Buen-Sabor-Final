package supercell.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import supercell.Models.Category;
import supercell.repository.CategoryRepository;
import supercell.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategories() {
        return List.of();
    }

    @Override
    public Category updateCategory(Category article) {
        return null;
    }

    @Override
    public Category addCategory(Category article) {
        return null;
    }

    @Override
    public Category deleteCategory(Category article) {
        return null;
    }
}
