package supercell.ElBuenSabor.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import supercell.ElBuenSabor.Models.Article;
import supercell.ElBuenSabor.Models.Category;
import supercell.ElBuenSabor.Models.Country;
import supercell.ElBuenSabor.Models.Employee;
import supercell.ElBuenSabor.Models.InventoryImage;
import supercell.ElBuenSabor.Models.Location;
import supercell.ElBuenSabor.Models.ManufacturedArticle;
import supercell.ElBuenSabor.Models.ManufacturedArticleDetail;
import supercell.ElBuenSabor.Models.MeasuringUnit;
import supercell.ElBuenSabor.Models.Provider;
import supercell.ElBuenSabor.Models.Province;
import supercell.ElBuenSabor.Models.enums.Role;
import supercell.ElBuenSabor.Models.enums.Shift;
import supercell.ElBuenSabor.repository.ArticleRepository;
import supercell.ElBuenSabor.repository.CategoryRepository;
import supercell.ElBuenSabor.repository.CountryRepository;
import supercell.ElBuenSabor.repository.LocationRepository;
import supercell.ElBuenSabor.repository.ManufacturedArticleRepository;
import supercell.ElBuenSabor.repository.MeasuringUnitRepository;
import supercell.ElBuenSabor.repository.ProviderRepository;
import supercell.ElBuenSabor.repository.ProvinceRepository;

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
    private final CountryRepository countryRepository;
    @Autowired
    private final ProvinceRepository provinceRepository;
    @Autowired
    private final LocationRepository locationRepository;
    @Autowired
    private final supercell.ElBuenSabor.repository.EmployeeRepository employeeRepository;


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

        categories.add(Category.builder()
            .name("Licuado")
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
            
            Map<String, Category> categoryMap = categoryRepository.findAll().stream()
                .collect(Collectors.toMap(Category::getName, c -> c));
        
            ManufacturedArticle manufacturedArticle = ManufacturedArticle.builder()
                .name("Licuado Frutal")
                .description("Licuado de bananas y frutilla.")
                .price(50.0D)
                .isAvailable(true)
                .estimatedTimeMinutes(15)
                    .stock(85)
                .category(categoryMap.get("Licuado"))
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
    @Transactional
    public String initializeCountryAndProvince() {
    
        Country country = Country.builder()
            .name("Argentina")
            .build();
        countryRepository.save(country);
    
        Province province = Province.builder()
            .name("Mendoza")
            .country(country)
            .build();
        provinceRepository.save(province);
    
        List<String> locationNames = List.of(
            "Godoy Cruz", "Guaymallén", "Las Heras", "Maipú", "Luján de Cuyo",
            "San Martín", "Tunuyán", "Rivadavia", "Malargüe", "San Rafael"
        );
    
        for (String name : locationNames) {
            Location location = Location.builder()
                .name(name)
                .province(province)
                .build();
            locationRepository.save(location);
        }
    
        return "Countries, Provinces, and 10 Locations Initialized";
    }
    
    @Transactional
    public String initializeEmployees() {
        List<Employee> employees = new ArrayList<>();

        for (Role role : Role.values()) {
            Employee employee = new Employee();
            employee.setEmployeeRole(role);
            employee.setSalary(25000.0); 
            employee.setShift(Shift.MORNING); 
            employee.setUsername(role.name().toLowerCase() + "_user");
            employee.setPassword("password");
            employee.setName("Name_" + role.name());
            employee.setLastName("LastName_" + role.name());
            employee.setPhoneNumber("123456789");
            employee.setEmail(role.name().toLowerCase() + "@example.com");
            employee.setBirthDate(new java.util.Date());

            employees.add(employee);
        }

        employeeRepository.saveAll(employees);
        return "Employees Initialized";
    }
}

