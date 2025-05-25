package supercell.ElBuenSabor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import supercell.ElBuenSabor.Models.ManufacturedArticle;
import supercell.ElBuenSabor.Models.payload.ManufacturedArticleDTO;
import supercell.ElBuenSabor.service.impl.ManufacturedArticleServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/manufacturedArticle")
public class ManufacturedArticleController {
    @Autowired
    private ManufacturedArticleServiceImpl manufacturedArticleServiceImpl;

    @GetMapping("/getAll")
    public List<ManufacturedArticle> getAll(){
        return manufacturedArticleServiceImpl.getAllManufacturedArticle();
    }

    @PostMapping("/add")
    public ResponseEntity<ManufacturedArticle> addManufacturedArticle(@RequestBody ManufacturedArticleDTO manufacturedArticleDTO){
        return ResponseEntity.status(HttpStatus.OK).body(manufacturedArticleServiceImpl.addManufacturedArticle(manufacturedArticleDTO));
    }

    @PatchMapping("/update/{ID}")
    public ResponseEntity<ManufacturedArticle> updateManufacturedArticle(@PathVariable Long ID, @RequestBody ManufacturedArticleDTO manufacturedArticleDTO){
        return ResponseEntity.status(HttpStatus.OK).body(manufacturedArticleServiceImpl.updateManufacturedArticle(ID, manufacturedArticleDTO));
    }

    @DeleteMapping("/delete/{ID}")
    public ResponseEntity<Boolean> deleteManufacturedArticle(@PathVariable Long ID){
        return ResponseEntity.status(HttpStatus.OK).body(manufacturedArticleServiceImpl.deleteManufacturedArticle(ID));
    }
}
