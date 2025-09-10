package supercell.ElBuenSabor.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import supercell.ElBuenSabor.Models.Article;
import supercell.ElBuenSabor.Models.Category;
import supercell.ElBuenSabor.Models.InventoryImage;
import supercell.ElBuenSabor.Models.ManufacturedArticle;
import supercell.ElBuenSabor.Models.ManufacturedArticleDetail;
import supercell.ElBuenSabor.Models.payload.ManufacturedArticleDTO;
import supercell.ElBuenSabor.Models.payload.ManufacturedArticleDetailDTO;
import supercell.ElBuenSabor.repository.ArticleRepository;
import supercell.ElBuenSabor.repository.CategoryRepository;
import supercell.ElBuenSabor.repository.ManufacturedArticleRepository;
import supercell.ElBuenSabor.service.ManufacturedArticleService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManufacturedArticleServiceImpl implements ManufacturedArticleService {
    @Autowired
    private final ManufacturedArticleRepository manufacturedArticleRepository;
    @Autowired
    private final ArticleRepository articleRepository;
    @Autowired 
    private final CategoryRepository categoryRepository;
    @Autowired
    private final SaleServiceImpl saleService;

    @Override
    public List<ManufacturedArticle> getAllManufacturedArticle() {
        return manufacturedArticleRepository.findAll();
    }
    
    @Override
    @Transactional
    public ManufacturedArticle addManufacturedArticle(ManufacturedArticleDTO manufacturedArticleDTO) {       
        ManufacturedArticle manufacturedArticle = ManufacturedArticle.builder()
            .name(manufacturedArticleDTO.name())
            .description(manufacturedArticleDTO.description())
            .isAvailable(manufacturedArticleDTO.isAvailable())
            .estimatedTimeMinutes(manufacturedArticleDTO.estimatedTimeMinutes())
            .build();
    
        manufacturedArticle = manufacturedArticleRepository.save(manufacturedArticle);
    
        List<ManufacturedArticleDetail> details = new ArrayList<>();
        for (ManufacturedArticleDetailDTO detailDTO : manufacturedArticleDTO.manufacturedArticleDetail()) {
            Article article = articleRepository.findById(detailDTO.articleId())
                .orElseThrow(() -> new EntityNotFoundException("Artículo no encontrado con ID: " + detailDTO.articleId()));
    
            ManufacturedArticleDetail detail = ManufacturedArticleDetail.builder()
                .article(article)
                .quantity(detailDTO.quantity())
                .manufacturedArticle(manufacturedArticle) 
                .build();
    
            details.add(detail);
        }
        manufacturedArticle.setManufacturedArticleDetail(details);
    
        // --- Price calculation logic ---
        double autoPrice = details.stream()
            .mapToDouble(detail -> detail.getArticle().getBuyingPrice() * detail.getQuantity())
            .sum() * 1.25;
    
        if (manufacturedArticleDTO.price() != null && manufacturedArticleDTO.price() > 0) {
            manufacturedArticle.setPrice(manufacturedArticleDTO.price());
        } else {
            manufacturedArticle.setPrice(autoPrice);
        }
    
        InventoryImage inventoryImage = InventoryImage.builder()
            .imageData(manufacturedArticleDTO.inventoryImageDTO().imageData())
            .build();
        manufacturedArticle.setManufacInventoryImage(inventoryImage);
    
        Category category = categoryRepository.findById(manufacturedArticleDTO.category())
            .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada con el ID: " + manufacturedArticleDTO.category()));
        manufacturedArticle.setCategory(category);
    
        return manufacturedArticleRepository.save(manufacturedArticle);
    }
    
    
    
    
    @Override
    public ManufacturedArticle updateManufacturedArticle(Long ID, ManufacturedArticleDTO manufacturedArticleDTO) {
        return manufacturedArticleRepository.findById(ID).map(existingManufacturedArticle -> {
            if (manufacturedArticleDTO.name() != null) {
                existingManufacturedArticle.setName(manufacturedArticleDTO.name());
            }
            if (manufacturedArticleDTO.description() != null) {
                existingManufacturedArticle.setDescription(manufacturedArticleDTO.description());
            }
            if (manufacturedArticleDTO.estimatedTimeMinutes() != 0) {
                existingManufacturedArticle.setEstimatedTimeMinutes(manufacturedArticleDTO.estimatedTimeMinutes());
            }
            existingManufacturedArticle.setAvailable(manufacturedArticleDTO.isAvailable());
    
            if (manufacturedArticleDTO.manufacturedArticleDetail() != null && !manufacturedArticleDTO.manufacturedArticleDetail().isEmpty()) {
                List<ManufacturedArticleDetail> details = new ArrayList<>();
                for (ManufacturedArticleDetailDTO detailDTO : manufacturedArticleDTO.manufacturedArticleDetail()) {
                    Article article = articleRepository.findById(detailDTO.articleId())
                        .orElseThrow(() -> new EntityNotFoundException("Artículo no encontrado con ID: " + detailDTO.articleId()));
    
                    ManufacturedArticleDetail detail = ManufacturedArticleDetail.builder()
                        .article(article)
                        .quantity(detailDTO.quantity())
                        .manufacturedArticle(existingManufacturedArticle) 
                        .build();
    
                    details.add(detail);
                }
                existingManufacturedArticle.getManufacturedArticleDetail().clear();
                existingManufacturedArticle.getManufacturedArticleDetail().addAll(details);
            }
    
            // --- PRICE LOGIC ---
            if (manufacturedArticleDTO.price() != null && manufacturedArticleDTO.price() > 0) {
                // manual override
                existingManufacturedArticle.setPrice(manufacturedArticleDTO.price());
            } else {
                // recalc auto price based on details
                double autoPrice = existingManufacturedArticle.getManufacturedArticleDetail().stream()
                    .mapToDouble(detail -> detail.getArticle().getBuyingPrice() * detail.getQuantity())
                    .sum() * 1.25;
                existingManufacturedArticle.setPrice(autoPrice);
            }
    
            // notify sales that depend on this manufacturedArticle
            saleService.updateSalePricesUsingManufacturedArticle(existingManufacturedArticle);
    
            if (manufacturedArticleDTO.inventoryImageDTO() != null) {
                InventoryImage inventoryImage = InventoryImage.builder()
                    .imageData(manufacturedArticleDTO.inventoryImageDTO().imageData())
                    .build();
                existingManufacturedArticle.setManufacInventoryImage(inventoryImage);
            }
    
            if (manufacturedArticleDTO.category() != null && manufacturedArticleDTO.category() != 0) {
                Category category = categoryRepository.findById(manufacturedArticleDTO.category())
                    .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada con el ID: " + manufacturedArticleDTO.category()));
                existingManufacturedArticle.setCategory(category);
            }
    
            return manufacturedArticleRepository.save(existingManufacturedArticle);
        }).orElseThrow(() -> new EntityNotFoundException("No se encontro un articulo manufacturado con el ID: " + ID));
    }
    
    

    @Transactional
    public void updatePricesForManufacturedArticlesUsingArticle(Article updatedArticle) {
        List<ManufacturedArticle> affectedArticles = manufacturedArticleRepository.findAll();
    
        for (ManufacturedArticle ma : affectedArticles) {
            boolean usesUpdatedArticle = ma.getManufacturedArticleDetail().stream()
                .anyMatch(detail -> detail.getArticle().getIDArticle().equals(updatedArticle.getIDArticle()));
    
            if (usesUpdatedArticle) {
                double totalPrice = ma.getManufacturedArticleDetail().stream()
                    .mapToDouble(detail -> detail.getArticle().getBuyingPrice() * detail.getQuantity())
                    .sum();
                ma.setPrice(totalPrice * 1.25); 
                manufacturedArticleRepository.save(ma);
    
                saleService.updateSalePricesUsingManufacturedArticle(ma);
            }
        }
    }
    
}
