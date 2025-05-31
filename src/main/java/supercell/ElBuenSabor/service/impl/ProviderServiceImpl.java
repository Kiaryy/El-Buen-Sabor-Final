package supercell.ElBuenSabor.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import supercell.ElBuenSabor.Models.Article;
import supercell.ElBuenSabor.Models.Category;
import supercell.ElBuenSabor.Models.Provider;
import supercell.ElBuenSabor.Models.payload.ProviderDTO;
import supercell.ElBuenSabor.repository.ArticleRepository;
import supercell.ElBuenSabor.repository.CategoryRepository;
import supercell.ElBuenSabor.repository.ProviderRepository;
import supercell.ElBuenSabor.service.ProviderService;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService{
    @Autowired
    private final ProviderRepository providerRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final ArticleRepository articleRepository;

    @Override
    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    @Override
    public Provider addProvider(ProviderDTO providerDTO) {
        Optional<Category> category = categoryRepository.findById(providerDTO.category());

        List<Article> articles = new ArrayList<>();

        for (Long articleID : providerDTO.articles()) {
            articles.add(articleRepository.findById(articleID).get());
        }

        Provider provider = Provider.builder()
            .lastShipmentDate(null)
            .shippingCost(providerDTO.shippingCost())
            .category(category.get())
            .articles(articles)
            .build();
        
        return providerRepository.save(provider);
    }

    @Override
    public Provider updateProvider(Long ID, ProviderDTO providerDTO) {



        return providerRepository.findById(ID).map(existingProvider ->{
            if (providerDTO.shippingCost() != null) {
                existingProvider.setShippingCost(providerDTO.shippingCost());
            }
            if (providerDTO.category() != 0) {
                Optional<Category> category = categoryRepository.findById(providerDTO.category());
                existingProvider.setCategory(category.get());
            }
            if (!providerDTO.articles().isEmpty()) {
                List<Article> articles = new ArrayList<>();

                for (Long articleID : providerDTO.articles()) {
                    articles.add(articleRepository.findById(articleID).get());
                }
                existingProvider.setArticles(articles);
            }
            return providerRepository.save(existingProvider);
        }).orElseThrow(() -> new EntityNotFoundException("No se encontro un proveedor con el ID: " + ID));

    }    
}
