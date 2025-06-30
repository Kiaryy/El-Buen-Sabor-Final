package supercell.ElBuenSabor.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import supercell.ElBuenSabor.Models.Article;
import supercell.ElBuenSabor.Models.Category;
import supercell.ElBuenSabor.Models.InventoryImage;
import supercell.ElBuenSabor.Models.MeasuringUnit;
import supercell.ElBuenSabor.Models.payload.ArticleDTO;
import supercell.ElBuenSabor.repository.ArticleRepository;
import supercell.ElBuenSabor.repository.CategoryRepository;
import supercell.ElBuenSabor.repository.MeasuringUnitRepository;
import supercell.ElBuenSabor.service.ArticleService;
import supercell.ElBuenSabor.service.ManufacturedArticleService;

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
    @Autowired
    private ManufacturedArticleServiceImpl manufacturedArticleService;
    @Autowired
    private final SaleServiceImpl saleService;



    @Override
    @Transactional
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    @Transactional
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
            .isForSale(articleDTO.isForSale())
            .build();

        InventoryImage inventoryImage = InventoryImage.builder()
            .imageData(articleDTO.inventoryImageDTO().imageData())
            .build();

        article.setInventoryImage(inventoryImage);
        return articleRepository.save(article);
    }

    @Override
    @Transactional
    public Article updateArticle(Long ID, ArticleDTO articleDTO) {
        return articleRepository.findById(ID).map(existingArticle -> {
    
            Double oldPrice = existingArticle.getBuyingPrice();
            Double newPrice = articleDTO.buyingPrice();
    
            if (articleDTO.denomination() != null) {
                existingArticle.setDenomination(articleDTO.denomination());
            }
            if (articleDTO.currentStock() != 0) {
                existingArticle.setCurrentStock(articleDTO.currentStock());
            }
            if (articleDTO.maxStock() != 0) {
                existingArticle.setMaxStock(articleDTO.maxStock());
            }
            if (newPrice != null) {
                existingArticle.setBuyingPrice(newPrice);
            }
    
            if (articleDTO.category() != null && articleDTO.category() != 0) {
                Optional<Category> category = categoryRepository.findById(articleDTO.category());
                existingArticle.setCategory(category.orElseThrow(() -> new EntityNotFoundException("Category not found")));
            }
            if (articleDTO.measuringUnit() != null && articleDTO.measuringUnit() != 0) {
                Optional<MeasuringUnit> measuringUnit = measuringUnitRepository.findById(articleDTO.measuringUnit());
                existingArticle.setMeasuringUnit(measuringUnit.orElseThrow(() -> new EntityNotFoundException("MeasuringUnit not found")));
            }
            if (articleDTO.isForSale() || !articleDTO.isForSale()) {
                existingArticle.setForSale(articleDTO.isForSale());
            }
            if (articleDTO.inventoryImageDTO() != null) {
                InventoryImage inventoryImage = InventoryImage.builder()
                    .imageData(articleDTO.inventoryImageDTO().imageData())
                    .build();
    
                existingArticle.setInventoryImage(inventoryImage);
            }
    
            Article saved = articleRepository.save(existingArticle);
    
            if (newPrice != null && !newPrice.equals(oldPrice)) {
                manufacturedArticleService.updatePricesForManufacturedArticlesUsingArticle(saved);
                saleService.updateSalePricesUsingArticle(saved);
            }
    
            return saved;
        }).orElseThrow(() -> new EntityNotFoundException("No se encontro un articulo con el ID: " + ID));
    }
    
}
