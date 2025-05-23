package supercell.ElBuenSabor.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import supercell.ElBuenSabor.Models.Article;
import supercell.ElBuenSabor.Models.Category;
import supercell.ElBuenSabor.Models.MeasuringUnit;
import supercell.ElBuenSabor.Models.Provider;
import supercell.ElBuenSabor.repository.ArticleRepository;
import supercell.ElBuenSabor.repository.CategoryRepository;
import supercell.ElBuenSabor.repository.MeasuringUnitRepository;
import supercell.ElBuenSabor.repository.ProviderRepository;

@Service
@RequiredArgsConstructor
public class InitializerService {
    @Autowired
    private final ArticleRepository articleRepository;
    @Autowired
    private final MeasuringUnitRepository measuringUnitRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final ProviderRepository providerRepository; 
    

    public String initializeCategory(){
        List<Category> categories = new ArrayList<>();
        
        categories.add(Category.builder()
            .name("Vegetal")
            .build()
        );

        categories.add(Category.builder()
            .name("Fruta")
            .build()
        );

        categoryRepository.saveAll(categories);
        return "Categories Initialized";
    }

    public String initializeMeasuringUnit(){
        List<MeasuringUnit> measuringUnits = new ArrayList<>();

        measuringUnits.add(MeasuringUnit.builder()
            .unit("Gr")
            .build()
        );

        measuringUnits.add(MeasuringUnit.builder()
            .unit("Kg")
            .build()
        );

        measuringUnitRepository.saveAll(measuringUnits);
        return "Measuring Units Initialized";
    }
    
    public String initializeArticle(){
        Map<String, Category> categoryMap = categoryRepository.findAll().stream()
            .collect(Collectors.toMap(Category::getName, c -> c));

        Map<String, MeasuringUnit> unitMap = measuringUnitRepository.findAll().stream()
            .collect(Collectors.toMap(MeasuringUnit::getUnit, u -> u));

        
        List<Article> articles = new ArrayList<>();
        
        
        articles.add(Article.builder()
            .denomination("Tomate")
            .currentStock(10)
            .maxStock(50)
            .buyingPrice(50.0D)
            .category(categoryMap.get("Vegetal")) 
            .measuringUnit(unitMap.get("Kg")) 
            .build()
        );

        articles.add(Article.builder()
            .denomination("Banana")
            .currentStock(25)
            .maxStock(25)
            .buyingPrice(10.50D)
            .category(categoryMap.get("Fruta")) 
            .measuringUnit(unitMap.get("Gr")) 
            .build()
        );

        articleRepository.saveAll(articles);
        return "Articles Initialized";
    }

    public String initializeProvider(){
        Map<String, Category> categoryMap = categoryRepository.findAll().stream()
            .collect(Collectors.toMap(Category::getName, c -> c));

        Map<String, Article> articleMap = articleRepository.findAll().stream()
            .collect(Collectors.toMap(Article::getDenomination, a -> a));
        List<Provider> providers = new ArrayList<>();

        providers.add(Provider.builder()
            .lastShipmentDate(LocalDate.now())
            .shippingCost(50.0D)
            .category(categoryMap.get("Fruta"))
            .articles(List.of(articleMap.get("Banana")))
            .build()
        );

        providers.add(Provider.builder()
            .lastShipmentDate(LocalDate.now())
            .shippingCost(50.0D)
            .category(categoryMap.get("Vegetal"))
            .articles(List.of(articleMap.get("Tomate")))
            .build()
        );
        providerRepository.saveAll(providers);
        return "Providers Initialized";
    }
}
