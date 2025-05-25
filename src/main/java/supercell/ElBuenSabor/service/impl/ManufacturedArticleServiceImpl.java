package supercell.ElBuenSabor.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import supercell.ElBuenSabor.Models.Article;
import supercell.ElBuenSabor.Models.ManufacturedArticle;
import supercell.ElBuenSabor.Models.ManufacturedArticleDetail;
import supercell.ElBuenSabor.Models.payload.ManufacturedArticleDTO;
import supercell.ElBuenSabor.Models.payload.ManufacturedArticleDetailDTO;
import supercell.ElBuenSabor.repository.ArticleRepository;
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
            .price(manufacturedArticleDTO.price())
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
    
        return manufacturedArticleRepository.save(manufacturedArticle);
    }
    
    
    
    @Override
    public ManufacturedArticle updateManufacturedArticle(Long ID, ManufacturedArticleDTO manufacturedArticleDTO) {
        return manufacturedArticleRepository.findById(ID).map(existingManufacturedArticle ->{
            if (manufacturedArticleDTO.description() != null) {
                existingManufacturedArticle.setDescription(manufacturedArticleDTO.description());
            }
            if (manufacturedArticleDTO.price() != 0) {
                existingManufacturedArticle.setPrice(manufacturedArticleDTO.price());
            }
            if (manufacturedArticleDTO.estimatedTimeMinutes() != 0) {
                existingManufacturedArticle.setEstimatedTimeMinutes(manufacturedArticleDTO.estimatedTimeMinutes());
            }
            if (manufacturedArticleDTO.isAvailable() != false) {
                existingManufacturedArticle.setAvailable(manufacturedArticleDTO.isAvailable());
            }
            return manufacturedArticleRepository.save(existingManufacturedArticle);
        }).orElseThrow(() -> new EntityNotFoundException("No se encontro una unidad de medida con el ID: " + ID));
        
    }

    @Override
    public boolean deleteManufacturedArticle(Long ID) {
        if (manufacturedArticleRepository.existsById(ID)) {
            manufacturedArticleRepository.deleteById(ID);
            return true;
        } else{
            throw new EntityNotFoundException("No se encontro una unidad de medida con el ID: " + ID);
        }
    }
}
