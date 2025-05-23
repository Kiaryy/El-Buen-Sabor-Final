package supercell.ElBuenSabor.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import supercell.ElBuenSabor.Models.Article;
import supercell.ElBuenSabor.Models.Category;
import supercell.ElBuenSabor.Models.MeasuringUnit;
import supercell.ElBuenSabor.Models.payload.ArticleDTO;
import supercell.ElBuenSabor.repository.ArticleRepository;
import supercell.ElBuenSabor.repository.CategoryRepository;
import supercell.ElBuenSabor.repository.MeasuringUnitRepository;
import supercell.ElBuenSabor.service.ArticleService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private final ArticleRepository articleRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final MeasuringUnitRepository measuringUnitRepository;


    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Article addArticle(ArticleDTO articleDTO) {
        Optional<Category> category = categoryRepository.findById(articleDTO.category());
        Optional<MeasuringUnit> measuringUnit = measuringUnitRepository.findById(articleDTO.measuringUnit());
        
        Article article = Article.builder()
            .denomination(articleDTO.denomination())
            .currentStock(articleDTO.currentStock())
            .maxStock(articleDTO.maxStock())
            .buyingPrice(articleDTO.buyingPrice())
            .measuringUnit(measuringUnit.get())
            .category(category.get())
            .build();
        
        return articleRepository.save(article);
    }

    @Override
    public Article updateArticle(Long ID, ArticleDTO articleDTO) {
        Optional<Category> category = categoryRepository.findById(articleDTO.category());
        Optional<MeasuringUnit> measuringUnit = measuringUnitRepository.findById(articleDTO.measuringUnit());
        
        return articleRepository.findById(ID).map(existingArticle ->{
            if (articleDTO.denomination() != null) {
                existingArticle.setDenomination(articleDTO.denomination());
            }
            if (articleDTO.currentStock() != 0) {
                existingArticle.setCurrentStock(articleDTO.currentStock());
            }
            if (articleDTO.maxStock() != 0) {
                existingArticle.setMaxStock(articleDTO.maxStock());
            }
            if (articleDTO.buyingPrice() != 0) {
                existingArticle.setBuyingPrice(articleDTO.buyingPrice());
            }
            if (articleDTO.category() != 0) {
                existingArticle.setCategory(category.get());
            }
            if (articleDTO.measuringUnit() != 0) {
                existingArticle.setMeasuringUnit(measuringUnit.get());
            }
            return articleRepository.save(existingArticle);
        }).orElseThrow(() -> new EntityNotFoundException("No se encontro un articulo con el ID: "+ ID));
    }
    
    @Override
    public boolean deleteArticle(Long ID) {
        if (articleRepository.existsById(ID)) {
            articleRepository.deleteById(ID);
            return true;
        }else{
            throw new EntityNotFoundException("No se encontro un articulo con el ID: " + ID);
        }
    }
}
