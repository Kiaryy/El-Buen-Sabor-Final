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

import supercell.ElBuenSabor.Models.Article;
import supercell.ElBuenSabor.Models.payload.ArticleDTO;
import supercell.ElBuenSabor.service.impl.ArticleServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleServiceImpl articleServiceImpl;
    
    @GetMapping("/getAll")
    public List<Article> getAll(){
        return articleServiceImpl.getAllArticles();
    }

    @PostMapping("/add")
    public ResponseEntity<Article> addArticle(@RequestBody ArticleDTO articleDTO){
        return ResponseEntity.status(HttpStatus.OK).body(articleServiceImpl.addArticle(articleDTO));
    }

    @PatchMapping("/update/{ID}")
    public ResponseEntity<Article> updateArticle(@PathVariable("ID") Long ID, @RequestBody ArticleDTO articleDTO){
        return ResponseEntity.status(HttpStatus.OK).body(articleServiceImpl.updateArticle(ID, articleDTO));
    }
}
