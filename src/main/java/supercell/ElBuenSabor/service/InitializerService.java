package supercell.ElBuenSabor.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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


    public String initializeCategory() {
        List<Category> categories = List.of(
            Category.builder().name("Vegetal").isForSale(false).build(),
            Category.builder().name("Fruta").isForSale(false).build(),
            Category.builder().name("Lácteo").isForSale(false).build(),
            Category.builder().name("Carnes").isForSale(false).build(),
            Category.builder().name("Cereales y Harinas").isForSale(false).build(),
            Category.builder().name("Condimentos").isForSale(false).build(),
            Category.builder().name("Salsas").isForSale(false).build(),
            Category.builder().name("Bebida").isForSale(true).build(),
            Category.builder().name("Pizza").isForSale(true).build(),
            Category.builder().name("Ensalada").isForSale(true).build(),
            Category.builder().name("Hamburguesa").isForSale(true).build(),
            Category.builder().name("Pastas").isForSale(true).build(),
            Category.builder().name("Tarta").isForSale(true).build(),
            Category.builder().name("Empanada").isForSale(true).build(),
            Category.builder().name("Sopa").isForSale(true).build(),
            Category.builder().name("Carnes y Cortes").isForSale(true).build()

            
        );
    
        categoryRepository.saveAll(categories);
        return "Categories Initialized";
    }
    

    public String initializeMeasuringUnit() {
    List<MeasuringUnit> measuringUnits = List.of(
        MeasuringUnit.builder().unit("Kg").build(),
        MeasuringUnit.builder().unit("Gr").build(),
        MeasuringUnit.builder().unit("L").build(),
        MeasuringUnit.builder().unit("Unit").build()
    );

    measuringUnitRepository.saveAll(measuringUnits);
    return "Measuring Units Initialized";
}

    

    public String initializeArticle() {
        try {
            Map<String, Category> categoryMap = categoryRepository.findAll().stream()
                .collect(Collectors.toMap(Category::getName, c -> c));

            Map<String, MeasuringUnit> unitMap = measuringUnitRepository.findAll().stream()
                .collect(Collectors.toMap(MeasuringUnit::getUnit, u -> u));

            List<Article> articles = new ArrayList<>();

            // --- 40 artículos base (ingredientes) sin imagen ---
            articles.add(Article.builder().denomination("Tomate").currentStock(100).maxStock(200).buyingPrice(100.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Cebolla").currentStock(100).maxStock(200).buyingPrice(90.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Harina de trigo").currentStock(200).maxStock(500).buyingPrice(60.0).isForSale(false).category(categoryMap.get("Cereales y Harinas")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Agua").currentStock(500).maxStock(1000).buyingPrice(5.0).isForSale(false).category(categoryMap.get("Cereales y Harinas")).measuringUnit(unitMap.get("L")).build());
            articles.add(Article.builder().denomination("Levadura").currentStock(50).maxStock(100).buyingPrice(15.0).isForSale(false).category(categoryMap.get("Cereales y Harinas")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Sal").currentStock(100).maxStock(200).buyingPrice(5.0).isForSale(false).category(categoryMap.get("Condimentos")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Comino").currentStock(100).maxStock(200).buyingPrice(5.0).isForSale(false).category(categoryMap.get("Condimentos")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Azúcar").currentStock(100).maxStock(200).buyingPrice(10.0).isForSale(false).category(categoryMap.get("Condimentos")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Aceite de oliva").currentStock(50).maxStock(100).buyingPrice(250.0).isForSale(false).category(categoryMap.get("Condimentos")).measuringUnit(unitMap.get("L")).build());
            articles.add(Article.builder().denomination("Ajo").currentStock(20).maxStock(50).buyingPrice(30.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Pimiento rojo").currentStock(50).maxStock(100).buyingPrice(120.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Pimiento verde").currentStock(50).maxStock(100).buyingPrice(110.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Zanahoria").currentStock(60).maxStock(100).buyingPrice(70.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Lechuga").currentStock(30).maxStock(50).buyingPrice(80.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Unit")).build());
            articles.add(Article.builder().denomination("Espinaca").currentStock(30).maxStock(50).buyingPrice(80.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Unit")).build());
            articles.add(Article.builder().denomination("Rúcula").currentStock(20).maxStock(50).buyingPrice(90.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Unit")).build());
            articles.add(Article.builder().denomination("Papa").currentStock(100).maxStock(200).buyingPrice(60.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Queso mozzarella").currentStock(100).maxStock(200).buyingPrice(500.0).isForSale(false).category(categoryMap.get("Lácteo")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Queso rallado").currentStock(50).maxStock(100).buyingPrice(450.0).isForSale(false).category(categoryMap.get("Lácteo")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Jamón cocido").currentStock(50).maxStock(100).buyingPrice(400.0).isForSale(false).category(categoryMap.get("Carnes")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Huevo").currentStock(60).maxStock(100).buyingPrice(30.0).isForSale(false).category(categoryMap.get("Lácteo")).measuringUnit(unitMap.get("Unit")).build());
            articles.add(Article.builder().denomination("Pechuga de pollo").currentStock(100).maxStock(150).buyingPrice(600.0).isForSale(false).category(categoryMap.get("Carnes")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Carne picada").currentStock(80).maxStock(150).buyingPrice(700.0).isForSale(false).category(categoryMap.get("Carnes")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Albahaca").currentStock(10).maxStock(20).buyingPrice(50.0).isForSale(false).category(categoryMap.get("Condimentos")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Orégano").currentStock(10).maxStock(20).buyingPrice(40.0).isForSale(false).category(categoryMap.get("Condimentos")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Pimienta").currentStock(10).maxStock(20).buyingPrice(35.0).isForSale(false).category(categoryMap.get("Condimentos")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Aceitunas").currentStock(30).maxStock(60).buyingPrice(120.0).isForSale(false).category(categoryMap.get("Condimentos")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Champiñones").currentStock(30).maxStock(60).buyingPrice(150.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Maíz en grano").currentStock(30).maxStock(60).buyingPrice(100.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Ananá").currentStock(20).maxStock(40).buyingPrice(180.0).isForSale(false).category(categoryMap.get("Fruta")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Cebolla caramelizada").currentStock(30).maxStock(50).buyingPrice(140.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Bacon").currentStock(30).maxStock(50).buyingPrice(350.0).isForSale(false).category(categoryMap.get("Carnes")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Pan rallado").currentStock(50).maxStock(100).buyingPrice(90.0).isForSale(false).category(categoryMap.get("Cereales y Harinas")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Manteca").currentStock(30).maxStock(50).buyingPrice(200.0).isForSale(false).category(categoryMap.get("Lácteo")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Crema de leche").currentStock(30).maxStock(50).buyingPrice(180.0).isForSale(false).category(categoryMap.get("Lácteo")).measuringUnit(unitMap.get("L")).build());
            articles.add(Article.builder().denomination("Salsa de tomate").currentStock(100).maxStock(150).buyingPrice(100.0).isForSale(false).category(categoryMap.get("Salsas")).measuringUnit(unitMap.get("L")).build());
            articles.add(Article.builder().denomination("Fideos").currentStock(100).maxStock(150).buyingPrice(70.0).isForSale(false).category(categoryMap.get("Cereales y Harinas")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Pan de hamburguesa").currentStock(30).maxStock(50).buyingPrice(60.0).isForSale(false).category(categoryMap.get("Cereales y Harinas")).measuringUnit(unitMap.get("Unit")).build());
            articles.add(Article.builder().denomination("Pepino").currentStock(30).maxStock(50).buyingPrice(60.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Unit")).build());
            articles.add(Article.builder().denomination("Mostaza").currentStock(20).maxStock(30).buyingPrice(90.0).isForSale(false).category(categoryMap.get("Salsas")).measuringUnit(unitMap.get("L")).build());
            articles.add(Article.builder().denomination("Ketchup").currentStock(20).maxStock(30).buyingPrice(90.0).isForSale(false).category(categoryMap.get("Salsas")).measuringUnit(unitMap.get("L")).build());

            // --- Crear imágenes para las bebidas ---
            InventoryImage imgCocaCola = InventoryImage.builder()
                .imageData(loadImageBytes("images/coca_cola_500ml.png"))
                .build();
            InventoryImage imgSprite = InventoryImage.builder()
                .imageData(loadImageBytes("images/sprite_500ml.png"))
                .build();
            InventoryImage imgFanta = InventoryImage.builder()
                .imageData(loadImageBytes("images/fanta_500ml.png"))
                .build();
            InventoryImage imgAguaSinGas = InventoryImage.builder()
                .imageData(loadImageBytes("images/agua_sin_gas_500ml.png"))
                .build();
            InventoryImage imgAguaConGas = InventoryImage.builder()
                .imageData(loadImageBytes("images/agua_con_gas_500ml.png"))
                .build();
            InventoryImage imgJugoNaranja = InventoryImage.builder()
                .imageData(loadImageBytes("images/jugo_naranja.png"))
                .build();
            InventoryImage imgJugoManzana = InventoryImage.builder()
                .imageData(loadImageBytes("images/jugo_manzana.png"))
                .build();
            InventoryImage imgTeFrioLimon = InventoryImage.builder()
                .imageData(loadImageBytes("images/te_frio_limon.png"))
                .build();
            InventoryImage imgCocaLight = InventoryImage.builder()
                .imageData(loadImageBytes("images/coca_light_500ml.png"))
                .build();
            InventoryImage imgLimaLimon = InventoryImage.builder()
                .imageData(loadImageBytes("images/gaseosa_lima_limon.png"))
                .build();
            InventoryImage imgCervezaRubia = InventoryImage.builder()
                .imageData(loadImageBytes("images/cerveza_rubia_500ml.png"))
                .build();
            InventoryImage imgCervezaNegra = InventoryImage.builder()
                .imageData(loadImageBytes("images/cerveza_negra_500ml.png"))
                .build();
            InventoryImage imgVinoTinto = InventoryImage.builder()
                .imageData(loadImageBytes("images/vino_tinto_750ml.png"))
                .build();
            InventoryImage imgVinoBlanco = InventoryImage.builder()
                .imageData(loadImageBytes("images/vino_blanco_750ml.png"))
                .build();
            InventoryImage imgLimonada = InventoryImage.builder()
                .imageData(loadImageBytes("images/limonada_casera.png"))
                .build();

            // --- 15 artículos para vender (bebidas) con imagen ---
            articles.add(Article.builder().denomination("Coca Cola 500ml").currentStock(50).maxStock(100).buyingPrice(120.0).isForSale(true).category(categoryMap.get("Bebida")).measuringUnit(unitMap.get("Unit")).inventoryImage(imgCocaCola).build());
            articles.add(Article.builder().denomination("Sprite 500ml").currentStock(50).maxStock(100).buyingPrice(120.0).isForSale(true).category(categoryMap.get("Bebida")).measuringUnit(unitMap.get("Unit")).inventoryImage(imgSprite).build());
            articles.add(Article.builder().denomination("Fanta 500ml").currentStock(50).maxStock(100).buyingPrice(120.0).isForSale(true).category(categoryMap.get("Bebida")).measuringUnit(unitMap.get("Unit")).inventoryImage(imgFanta).build());
            articles.add(Article.builder().denomination("Agua sin gas 500ml").currentStock(50).maxStock(100).buyingPrice(80.0).isForSale(true).category(categoryMap.get("Bebida")).measuringUnit(unitMap.get("Unit")).inventoryImage(imgAguaSinGas).build());
            articles.add(Article.builder().denomination("Agua con gas 500ml").currentStock(50).maxStock(100).buyingPrice(80.0).isForSale(true).category(categoryMap.get("Bebida")).measuringUnit(unitMap.get("Unit")).inventoryImage(imgAguaConGas).build());
            articles.add(Article.builder().denomination("Jugo de naranja").currentStock(40).maxStock(80).buyingPrice(100.0).isForSale(true).category(categoryMap.get("Bebida")).measuringUnit(unitMap.get("Unit")).inventoryImage(imgJugoNaranja).build());
            articles.add(Article.builder().denomination("Jugo de manzana").currentStock(40).maxStock(80).buyingPrice(100.0).isForSale(true).category(categoryMap.get("Bebida")).measuringUnit(unitMap.get("Unit")).inventoryImage(imgJugoManzana).build());
            articles.add(Article.builder().denomination("Té frío limón").currentStock(40).maxStock(80).buyingPrice(90.0).isForSale(true).category(categoryMap.get("Bebida")).measuringUnit(unitMap.get("Unit")).inventoryImage(imgTeFrioLimon).build());
            articles.add(Article.builder().denomination("Coca Light 500ml").currentStock(50).maxStock(100).buyingPrice(120.0).isForSale(true).category(categoryMap.get("Bebida")).measuringUnit(unitMap.get("Unit")).inventoryImage(imgCocaLight).build());
            articles.add(Article.builder().denomination("Gaseosa lima limón").currentStock(50).maxStock(100).buyingPrice(120.0).isForSale(true).category(categoryMap.get("Bebida")).measuringUnit(unitMap.get("Unit")).inventoryImage(imgLimaLimon).build());
            articles.add(Article.builder().denomination("Cerveza rubia 500ml").currentStock(30).maxStock(60).buyingPrice(150.0).isForSale(true).category(categoryMap.get("Bebida")).measuringUnit(unitMap.get("Unit")).inventoryImage(imgCervezaRubia).build());
            articles.add(Article.builder().denomination("Cerveza negra 500ml").currentStock(30).maxStock(60).buyingPrice(150.0).isForSale(true).category(categoryMap.get("Bebida")).measuringUnit(unitMap.get("Unit")).inventoryImage(imgCervezaNegra).build());
            articles.add(Article.builder().denomination("Vino tinto 750ml").currentStock(20).maxStock(40).buyingPrice(400.0).isForSale(true).category(categoryMap.get("Bebida")).measuringUnit(unitMap.get("Unit")).inventoryImage(imgVinoTinto).build());
            articles.add(Article.builder().denomination("Vino blanco 750ml").currentStock(20).maxStock(40).buyingPrice(400.0).isForSale(true).category(categoryMap.get("Bebida")).measuringUnit(unitMap.get("Unit")).inventoryImage(imgVinoBlanco).build());
            articles.add(Article.builder().denomination("Limonada casera").currentStock(40).maxStock(80).buyingPrice(100.0).isForSale(true).category(categoryMap.get("Bebida")).measuringUnit(unitMap.get("Unit")).inventoryImage(imgLimonada).build());

            articleRepository.saveAll(articles);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Articles Initialized";
    }

    private byte[] loadImageBytes(String path) throws IOException {
        File file = new File(path);
        return Files.readAllBytes(file.toPath());
    }



    @Transactional
public String initializeProvider() {
    Map<String, Category> categoryMap = categoryRepository.findAll().stream()
        .collect(Collectors.toMap(Category::getName, c -> c));

    Map<String, Article> articleMap = articleRepository.findAll().stream()
        .collect(Collectors.toMap(Article::getDenomination, a -> a));

    List<Provider> providers = new ArrayList<>();

    providers.add(Provider.builder()
        .lastShipmentDate(LocalDate.now())
        .shippingCost(200.0D)
        .category(requireCategory(categoryMap, "Vegetal"))
        .articles(List.of(
            requireArticle(articleMap, "Tomate"),
            requireArticle(articleMap, "Cebolla"),
            requireArticle(articleMap, "Zanahoria"),
            requireArticle(articleMap, "Lechuga"),
            requireArticle(articleMap, "Pimiento rojo"),
            requireArticle(articleMap, "Pimiento verde"),
            requireArticle(articleMap, "Cebolla caramelizada")))
        .build());

    providers.add(Provider.builder()
        .lastShipmentDate(LocalDate.now())
        .shippingCost(150.0D)
        .category(requireCategory(categoryMap, "Fruta"))
        .articles(List.of(
            requireArticle(articleMap, "Ananá")))
        .build());

    providers.add(Provider.builder()
        .lastShipmentDate(LocalDate.now())
        .shippingCost(170.0D)
        .category(requireCategory(categoryMap, "Lácteo"))
        .articles(List.of(
            requireArticle(articleMap, "Queso mozzarella"),
            requireArticle(articleMap, "Queso rallado"),
            requireArticle(articleMap, "Huevo"),
            requireArticle(articleMap, "Manteca"),
            requireArticle(articleMap, "Crema de leche")))
        .build());

    providers.add(Provider.builder()
        .lastShipmentDate(LocalDate.now())
        .shippingCost(160.0D)
        .category(requireCategory(categoryMap, "Carnes"))
        .articles(List.of(
            requireArticle(articleMap, "Jamón cocido"),
            requireArticle(articleMap, "Pechuga de pollo"),
            requireArticle(articleMap, "Carne picada"),
            requireArticle(articleMap, "Bacon")))
        .build());

    providers.add(Provider.builder()
        .lastShipmentDate(LocalDate.now())
        .shippingCost(180.0D)
        .category(requireCategory(categoryMap, "Cereales y Harinas"))
        .articles(List.of(
            requireArticle(articleMap, "Harina de trigo"),
            requireArticle(articleMap, "Agua"),
            requireArticle(articleMap, "Levadura"),
            requireArticle(articleMap, "Pan rallado"),
            requireArticle(articleMap, "Fideos"),
            requireArticle(articleMap, "Pan de hamburguesa")))
        .build());

    providers.add(Provider.builder()
        .lastShipmentDate(LocalDate.now())
        .shippingCost(130.0D)
        .category(requireCategory(categoryMap, "Condimentos"))
        .articles(List.of(
            requireArticle(articleMap, "Sal"),
            requireArticle(articleMap, "Azúcar"),
            requireArticle(articleMap, "Aceite de oliva"),
            requireArticle(articleMap, "Ajo"),
            requireArticle(articleMap, "Albahaca"),
            requireArticle(articleMap, "Orégano"),
            requireArticle(articleMap, "Pimienta"),
            requireArticle(articleMap, "Aceitunas"),
            requireArticle(articleMap, "Comino")))
        .build());

    providers.add(Provider.builder()
        .lastShipmentDate(LocalDate.now())
        .shippingCost(140.0D)
        .category(requireCategory(categoryMap, "Salsas"))
        .articles(List.of(
            requireArticle(articleMap, "Salsa de tomate"),
            requireArticle(articleMap, "Mostaza"),
            requireArticle(articleMap, "Ketchup")))
        .build());

    providers.add(Provider.builder()
        .lastShipmentDate(LocalDate.now())
        .shippingCost(100.0D)
        .category(requireCategory(categoryMap, "Bebida"))
        .articles(articleMap.values().stream()
            .filter(a -> a.getCategory().getName().equals("Bebida"))
            .filter(Objects::nonNull)
            .collect(Collectors.toList()))
        .build());

    providerRepository.saveAll(providers);
    return "Providers Initialized";
}

private Article requireArticle(Map<String, Article> articleMap, String name) {
    return Objects.requireNonNull(articleMap.get(name), "Artículo no encontrado: " + name);
}

private Category requireCategory(Map<String, Category> categoryMap, String name) {
    return Objects.requireNonNull(categoryMap.get(name), "Categoría no encontrada: " + name);
}





    @Transactional
    public String initializeManufacturedArticle() {
        try {
            Map<String, Article> articleMap = articleRepository.findAll().stream()
                .collect(Collectors.toMap(Article::getDenomination, a -> a));

            Map<String, Category> categoryMap = categoryRepository.findAll().stream()
                .collect(Collectors.toMap(Category::getName, c -> c));

            List<ManufacturedArticle> manufacturedArticles = new ArrayList<>();

            manufacturedArticles.add(createManufactured("Pizza Margarita", "Pizza con salsa de tomate, mozzarella y albahaca", 1200.0, 25, categoryMap.get("Pizza"),
                Map.of("Salsa de tomate", 100, "Queso mozzarella", 150, "Albahaca", 10), "images/pizza_margarita.png"));

            manufacturedArticles.add(createManufactured("Ensalada Mixta", "Lechuga, tomate, cebolla y zanahoria", 800.0, 15, categoryMap.get("Ensalada"),
                Map.of("Lechuga", 1, "Tomate", 150, "Cebolla", 100, "Zanahoria", 100), "images/ensalada_mixta.png"));

            manufacturedArticles.add(createManufactured("Hamburguesa Completa", "Hamburguesa con pan, lechuga, tomate, queso y huevo", 1400.0, 20, categoryMap.get("Hamburguesa"),
                Map.of("Pan de hamburguesa", 1, "Carne picada", 150, "Lechuga", 1, "Tomate", 100, "Queso mozzarella", 100, "Huevo", 1), "images/hamburguesa_completa.png"));

            manufacturedArticles.add(createManufactured("Tarta de Verduras", "Tarta con espinaca, cebolla y huevo", 1000.0, 30, categoryMap.get("Tarta"),
                Map.of("Espinaca", 1, "Cebolla", 100, "Huevo", 2, "Harina de trigo", 100), "images/tarta_verduras.png"));

            manufacturedArticles.add(createManufactured("Lasaña", "Lasaña con carne, salsa y queso", 1600.0, 40, categoryMap.get("Pastas"),
                Map.of("Fideos", 200, "Carne picada", 200, "Queso mozzarella", 150, "Salsa de tomate", 100), "images/lasagna.png"));

            manufacturedArticles.add(createManufactured("Tortilla de Papas", "Clásica tortilla con huevo y papa", 900.0, 25, categoryMap.get("Tarta"),
                Map.of("Papa", 200, "Huevo", 3, "Cebolla", 50), "images/tortilla_papas.png"));

            manufacturedArticles.add(createManufactured("Pizza Fugazzeta", "Pizza con cebolla caramelizada y queso", 1300.0, 25, categoryMap.get("Pizza"),
                Map.of("Cebolla caramelizada", 100, "Queso mozzarella", 150, "Salsa de tomate", 80), "images/pizza_fugazzeta.png"));

            manufacturedArticles.add(createManufactured("Empanadas de Carne", "Empanadas con carne picada y condimentos", 900.0, 20, categoryMap.get("Empanada"),
                Map.of("Carne picada", 150, "Cebolla", 50, "Pimiento rojo", 50, "Comino", 5), "images/empanadas_carne.png"));

            manufacturedArticles.add(createManufactured("Sopa de Verduras", "Sopa nutritiva con varios vegetales", 700.0, 20, categoryMap.get("Sopa"),
                Map.of("Zanahoria", 50, "Papa", 100, "Cebolla", 50, "Pimiento verde", 50), "images/sopa_verduras.png"));

            manufacturedArticles.add(createManufactured("Milanesa de Pollo", "Pechuga empanada con pan rallado", 1100.0, 30, categoryMap.get("Carnes y Cortes"),
                Map.of("Pechuga de pollo", 200, "Pan rallado", 100, "Huevo", 1), "images/milanesa_pollo.png"));

            manufacturedArticles.add(createManufactured("Spaghetti con salsa rosa", "Fideos con salsa y crema", 1200.0, 25, categoryMap.get("Pastas"),
                Map.of("Fideos", 200, "Salsa de tomate", 100, "Crema de leche", 50), "images/spaghetti_salsa_rosa.png"));

            manufacturedArticles.add(createManufactured("Pizza Napolitana", "Pizza con tomate, ajo, albahaca y mozzarella", 1350.0, 30, categoryMap.get("Pizza"),
                Map.of("Tomate", 100, "Ajo", 10, "Albahaca", 5, "Queso mozzarella", 150), "images/pizza_napolitana.png"));

            manufacturedArticles.add(createManufactured("Sandwich de Jamón y Queso", "Clásico sandwich tostado", 850.0, 10, categoryMap.get("Carnes y Cortes"),
                Map.of("Pan de hamburguesa", 2, "Queso mozzarella", 100, "Jamón cocido", 100), "images/sandwich_jamon_queso.png"));

            manufacturedArticles.add(createManufactured("Pollo al Horno con Papas", "Pechuga con papas y condimentos", 1500.0, 35, categoryMap.get("Carnes y Cortes"),
                Map.of("Pechuga de pollo", 200, "Papa", 200, "Aceite de oliva", 20), "images/pollo_horno_papas.png"));

            manufacturedArticles.add(createManufactured("Pizza Hawaiana", "Pizza con jamón y ananá", 1400.0, 30, categoryMap.get("Pizza"),
                Map.of("Queso mozzarella", 150, "Jamón cocido", 100, "Ananá", 100), "images/pizza_hawaiana.png"));

            manufacturedArticleRepository.saveAll(manufacturedArticles);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Manufactured Articles Initialized";
    }

    private ManufacturedArticle createManufactured(String name, String description, Double price, int time, Category category, Map<String, Integer> ingredientMap, String imagePath) {
        try {
            Map<String, Article> articleMap = articleRepository.findAll().stream()
                .collect(Collectors.toMap(Article::getDenomination, a -> a));

            ManufacturedArticle manufacturedArticle = ManufacturedArticle.builder()
                .name(name)
                .description(description)
                .price(price)
                .isAvailable(true)
                .estimatedTimeMinutes(time)
                .stock(50)
                .category(category)
                .build();

            List<ManufacturedArticleDetail> details = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : ingredientMap.entrySet()) {
                Article article = articleMap.get(entry.getKey());
                if(article == null) {
                    throw new RuntimeException("No existe el artículo: " + entry.getKey());
                }
                details.add(ManufacturedArticleDetail.builder()
                    .article(article)
                    .quantity(entry.getValue())
                    .manufacturedArticle(manufacturedArticle)
                    .build());
            }

            // Carga la imagen desde el archivo y crea el InventoryImage
            InventoryImage inventoryImage = InventoryImage.builder()
                .imageData(loadImageBytes(imagePath))
                .build();

            manufacturedArticle.setManufacturedArticleDetail(details);
            manufacturedArticle.setManufacInventoryImage(inventoryImage);

            // Guardamos el ManufacturedArticle junto con sus detalles y la imagen
            return manufacturedArticleRepository.save(manufacturedArticle);

        } catch (IOException e) {
            throw new RuntimeException("Error leyendo la imagen: " + imagePath, e);
        }
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

