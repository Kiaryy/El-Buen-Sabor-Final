package supercell.ElBuenSabor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import supercell.ElBuenSabor.Models.Category;
import supercell.ElBuenSabor.Models.payload.CategoryDTO;
import supercell.ElBuenSabor.service.impl.CategoryServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @GetMapping("/getAll")
    public List<Category> findAll(){
        return categoryServiceImpl.getAllCategories();
    }

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.status(HttpStatus.OK).body(categoryServiceImpl.addCategory(categoryDTO));
    }

    @PatchMapping("/update/{ID}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long ID, @RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.status(HttpStatus.OK).body(categoryServiceImpl.updateCategory(ID, categoryDTO));
    }
}
