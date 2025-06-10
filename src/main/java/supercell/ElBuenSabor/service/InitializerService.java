package supercell.ElBuenSabor.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import supercell.ElBuenSabor.Models.*;
import supercell.ElBuenSabor.repository.*;

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
    @Autowired
    private final ManufacturedArticleRepository manufacturedArticleRepository;
    @Autowired
    private final ClientRepository clientRepository;
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

        articles.add(Article.builder()
        .denomination("Frutilla")
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
            .articles(List.of(articleMap.get("Banana"), articleMap.get("Frutilla")))
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

    @Transactional
    public String initializeManufacturedArticle() {
        try{
            Map<String, Article> articleMap = articleRepository.findAll().stream()
                .collect(Collectors.toMap(Article::getDenomination, a -> a));
        
            ManufacturedArticle manufacturedArticle = ManufacturedArticle.builder()
                .name("Licuado Frutal")
                .description("Licuado de bananas y frutilla.")
                .price(50.0D)
                .isAvailable(true)
                .estimatedTimeMinutes(15)
                .build();
        
            manufacturedArticle = manufacturedArticleRepository.save(manufacturedArticle);
        
            List<ManufacturedArticleDetail> details = new ArrayList<>();
        
            details.add(ManufacturedArticleDetail.builder()
                .article(articleMap.get("Banana"))
                .quantity(250)
                .manufacturedArticle(manufacturedArticle)
                .build());
        
            details.add(ManufacturedArticleDetail.builder()
                .article(articleMap.get("Frutilla"))
                .quantity(250)
                .manufacturedArticle(manufacturedArticle)
                .build());
    
        
            InventoryImage inventoryImage = InventoryImage.builder()
                .imageData(Files.readAllBytes(new File("MENU/screenshot-20250601-160119.png").toPath()))
                .build();
                
            manufacturedArticle.setManufacturedArticleDetail(details);
            manufacturedArticle.setManufacInventoryImage(inventoryImage);
            manufacturedArticleRepository.save(manufacturedArticle);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    
        return "ManufacturedArticles Initialized";
    }
    public String initializeClient(){
        try {
            Client client = new Client();
            client.setName("Juan");
            client.setEmail("juan@Gmail.com");
            client.setLastName("alberto");
            client.setPhoneNumber("2031545");
            client.setBirthDate(Date.valueOf("2000-01-15"));
            client.setUsername("Juan");
            client.setPassword("JuanPassword");
            client.setAuth0Id("1");
            clientRepository.save(client);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "client initialized";
    }
}
