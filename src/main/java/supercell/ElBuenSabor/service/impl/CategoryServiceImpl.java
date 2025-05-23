package supercell.ElBuenSabor.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import supercell.ElBuenSabor.Models.Category;
import supercell.ElBuenSabor.Models.payload.CategoryDTO;
import supercell.ElBuenSabor.repository.CategoryRepository;
import supercell.ElBuenSabor.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder()
            .name(categoryDTO.name())
            .build();
        return categoryRepository.save(category);
    }
    
    @Override
    public Category updateCategory(Long ID, CategoryDTO categoryDTO){
        return categoryRepository.findById(ID).map(existingCategory ->{
            if (categoryDTO.name() != null) {
                existingCategory.setName(categoryDTO.name());
            }
            return categoryRepository.save(existingCategory);
        }).orElseThrow(() -> new EntityNotFoundException("No se encontro una categoria con el ID: " + ID));
    }

    @Override
    public boolean deleteCategory(Long ID) {
        if (categoryRepository.existsById(ID)) {
            categoryRepository.deleteById(ID);
            return true;
        } else{
            throw new EntityNotFoundException("No se encontro una categoria con el ID: " + ID);
        }
    }
}
