package supercell.ElBuenSabor.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import supercell.ElBuenSabor.Models.*;

import supercell.ElBuenSabor.Models.enums.*;
import supercell.ElBuenSabor.Models.enums.PayMethod;
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
    private final CountryRepository countryRepository;
    @Autowired
    private final ProvinceRepository provinceRepository;
    @Autowired
    private final LocationRepository locationRepository;
    @Autowired
    private final EmployeeRepository employeeRepository;
    @Autowired
    private final ClientRepository clientRepository;
    @Autowired
    private final SaleRepository saleRepository;
    @Autowired
    private final OrderRepository orderRepository;

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
            articles.add(Article.builder().denomination("Tomate").currentStock(100).maxStock(200).buyingPrice(50.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Cebolla").currentStock(100).maxStock(200).buyingPrice(50.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Harina de trigo").currentStock(200).maxStock(500).buyingPrice(30.0).isForSale(false).category(categoryMap.get("Cereales y Harinas")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Agua").currentStock(500).maxStock(1000).buyingPrice(5.0).isForSale(false).category(categoryMap.get("Cereales y Harinas")).measuringUnit(unitMap.get("L")).build());
            articles.add(Article.builder().denomination("Levadura").currentStock(50).maxStock(100).buyingPrice(15.0).isForSale(false).category(categoryMap.get("Cereales y Harinas")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Sal").currentStock(100).maxStock(200).buyingPrice(5.0).isForSale(false).category(categoryMap.get("Condimentos")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Comino").currentStock(100).maxStock(200).buyingPrice(5.0).isForSale(false).category(categoryMap.get("Condimentos")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Azúcar").currentStock(100).maxStock(200).buyingPrice(10.0).isForSale(false).category(categoryMap.get("Condimentos")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Aceite de oliva").currentStock(50).maxStock(100).buyingPrice(100.0).isForSale(false).category(categoryMap.get("Condimentos")).measuringUnit(unitMap.get("L")).build());
            articles.add(Article.builder().denomination("Ajo").currentStock(20).maxStock(50).buyingPrice(30.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Pimiento rojo").currentStock(50).maxStock(100).buyingPrice(50.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Pimiento verde").currentStock(50).maxStock(100).buyingPrice(45.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Zanahoria").currentStock(60).maxStock(100).buyingPrice(45.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Lechuga").currentStock(30).maxStock(50).buyingPrice(40.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Unit")).build());
            articles.add(Article.builder().denomination("Espinaca").currentStock(30).maxStock(50).buyingPrice(45.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Unit")).build());
            articles.add(Article.builder().denomination("Rúcula").currentStock(20).maxStock(50).buyingPrice(55.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Unit")).build());
            articles.add(Article.builder().denomination("Papa").currentStock(100).maxStock(200).buyingPrice(45.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Queso mozzarella").currentStock(100).maxStock(200).buyingPrice(150.0).isForSale(false).category(categoryMap.get("Lácteo")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Queso rallado").currentStock(50).maxStock(100).buyingPrice(150.0).isForSale(false).category(categoryMap.get("Lácteo")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Jamón cocido").currentStock(50).maxStock(100).buyingPrice(145.0).isForSale(false).category(categoryMap.get("Carnes")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Huevo").currentStock(60).maxStock(100).buyingPrice(30.0).isForSale(false).category(categoryMap.get("Lácteo")).measuringUnit(unitMap.get("Unit")).build());
            articles.add(Article.builder().denomination("Pechuga de pollo").currentStock(100).maxStock(150).buyingPrice(130.0).isForSale(false).category(categoryMap.get("Carnes")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Carne picada").currentStock(80).maxStock(150).buyingPrice(145.0).isForSale(false).category(categoryMap.get("Carnes")).measuringUnit(unitMap.get("Kg")).build());
            articles.add(Article.builder().denomination("Albahaca").currentStock(10).maxStock(20).buyingPrice(50.0).isForSale(false).category(categoryMap.get("Condimentos")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Orégano").currentStock(10).maxStock(20).buyingPrice(40.0).isForSale(false).category(categoryMap.get("Condimentos")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Pimienta").currentStock(10).maxStock(20).buyingPrice(35.0).isForSale(false).category(categoryMap.get("Condimentos")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Aceitunas").currentStock(30).maxStock(60).buyingPrice(120.0).isForSale(false).category(categoryMap.get("Condimentos")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Champiñones").currentStock(30).maxStock(60).buyingPrice(150.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Maíz en grano").currentStock(30).maxStock(60).buyingPrice(100.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Ananá").currentStock(20).maxStock(40).buyingPrice(155.0).isForSale(false).category(categoryMap.get("Fruta")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Cebolla caramelizada").currentStock(30).maxStock(50).buyingPrice(120.0).isForSale(false).category(categoryMap.get("Vegetal")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Bacon").currentStock(30).maxStock(50).buyingPrice(120.0).isForSale(false).category(categoryMap.get("Carnes")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Pan rallado").currentStock(50).maxStock(100).buyingPrice(90.0).isForSale(false).category(categoryMap.get("Cereales y Harinas")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Manteca").currentStock(30).maxStock(50).buyingPrice(145.0).isForSale(false).category(categoryMap.get("Lácteo")).measuringUnit(unitMap.get("Gr")).build());
            articles.add(Article.builder().denomination("Crema de leche").currentStock(30).maxStock(50).buyingPrice(130.0).isForSale(false).category(categoryMap.get("Lácteo")).measuringUnit(unitMap.get("L")).build());
            articles.add(Article.builder().denomination("Salsa de tomate").currentStock(100).maxStock(150).buyingPrice(45.0).isForSale(false).category(categoryMap.get("Salsas")).measuringUnit(unitMap.get("L")).build());
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
            throw new RuntimeException("Failed to initialize users", e);
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
            Map<String, Category> categoryMap = categoryRepository.findAll().stream()
                .collect(Collectors.toMap(Category::getName, c -> c));

            List<ManufacturedArticle> manufacturedArticles = new ArrayList<>();

            manufacturedArticles.add(createManufactured("Pizza Margarita", "Pizza con salsa de tomate, mozzarella y albahaca", 25, categoryMap.get("Pizza"),
                Map.of("Salsa de tomate", 100, "Queso mozzarella", 150, "Albahaca", 10), "images/pizza_margarita.png"));

            manufacturedArticles.add(createManufactured("Ensalada Mixta", "Lechuga, tomate, cebolla y zanahoria", 15, categoryMap.get("Ensalada"),
                Map.of("Lechuga", 1, "Tomate", 150, "Cebolla", 100, "Zanahoria", 100), "images/ensalada_mixta.png"));

            manufacturedArticles.add(createManufactured("Hamburguesa Completa", "Hamburguesa con pan, lechuga, tomate, queso y huevo", 20, categoryMap.get("Hamburguesa"),
                Map.of("Pan de hamburguesa", 1, "Carne picada", 150, "Lechuga", 1, "Tomate", 100, "Queso mozzarella", 100, "Huevo", 1), "images/hamburguesa_completa.png"));

            manufacturedArticles.add(createManufactured("Tarta de Verduras", "Tarta con espinaca, cebolla y huevo", 30, categoryMap.get("Tarta"),
                Map.of("Espinaca", 1, "Cebolla", 100, "Huevo", 2, "Harina de trigo", 100), "images/tarta_verduras.png"));

            manufacturedArticles.add(createManufactured("Lasaña", "Lasaña con carne, salsa y queso", 40, categoryMap.get("Pastas"),
                Map.of("Fideos", 200, "Carne picada", 200, "Queso mozzarella", 150, "Salsa de tomate", 100), "images/lasagna.png"));

            manufacturedArticles.add(createManufactured("Tortilla de Papas", "Clásica tortilla con huevo y papa", 25, categoryMap.get("Tarta"),
                Map.of("Papa", 200, "Huevo", 3, "Cebolla", 50), "images/tortilla_papas.png"));

            manufacturedArticles.add(createManufactured("Pizza Fugazzeta", "Pizza con cebolla caramelizada y queso", 25, categoryMap.get("Pizza"),
                Map.of("Cebolla caramelizada", 100, "Queso mozzarella", 150, "Salsa de tomate", 80), "images/pizza_fugazzeta.png"));

            manufacturedArticles.add(createManufactured("Empanadas de Carne", "Empanadas con carne picada y condimentos", 20, categoryMap.get("Empanada"),
                Map.of("Carne picada", 150, "Cebolla", 50, "Pimiento rojo", 50, "Comino", 5), "images/empanadas_carne.png"));

            manufacturedArticles.add(createManufactured("Sopa de Verduras", "Sopa nutritiva con varios vegetales", 20, categoryMap.get("Sopa"),
                Map.of("Zanahoria", 50, "Papa", 100, "Cebolla", 50, "Pimiento verde", 50), "images/sopa_verduras.png"));

            manufacturedArticles.add(createManufactured("Milanesa de Pollo", "Pechuga empanada con pan rallado", 30, categoryMap.get("Carnes y Cortes"),
                Map.of("Pechuga de pollo", 200, "Pan rallado", 100, "Huevo", 1), "images/milanesa_pollo.png"));

            manufacturedArticles.add(createManufactured("Spaghetti con salsa rosa", "Fideos con salsa y crema", 25, categoryMap.get("Pastas"),
                Map.of("Fideos", 200, "Salsa de tomate", 100, "Crema de leche", 50), "images/spaghetti_salsa_rosa.png"));

            manufacturedArticles.add(createManufactured("Pizza Napolitana", "Pizza con tomate, ajo, albahaca y mozzarella", 30, categoryMap.get("Pizza"),
                Map.of("Tomate", 100, "Ajo", 10, "Albahaca", 5, "Queso mozzarella", 150), "images/pizza_napolitana.png"));

            manufacturedArticles.add(createManufactured("Sandwich de Jamón y Queso", "Clásico sandwich tostado", 10, categoryMap.get("Carnes y Cortes"),
                Map.of("Pan de hamburguesa", 2, "Queso mozzarella", 100, "Jamón cocido", 100), "images/sandwich_jamon_queso.png"));

            manufacturedArticles.add(createManufactured("Pollo al Horno con Papas", "Pechuga con papas y condimentos", 35, categoryMap.get("Carnes y Cortes"),
                Map.of("Pechuga de pollo", 200, "Papa", 200, "Aceite de oliva", 20), "images/pollo_horno_papas.png"));

            manufacturedArticles.add(createManufactured("Pizza Hawaiana", "Pizza con jamón y ananá", 30, categoryMap.get("Pizza"),
                Map.of("Queso mozzarella", 150, "Jamón cocido", 100, "Ananá", 100), "images/pizza_hawaiana.png"));

            manufacturedArticleRepository.saveAll(manufacturedArticles);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize manufactured articles", e);
        }

        return "Manufactured Articles Initialized";
    }

    private ManufacturedArticle createManufactured(String name, String description, int time, Category category, Map<String, Integer> ingredientMap, String imagePath) {
        try {
            Map<String, Article> articleMap = articleRepository.findAll().stream()
                .collect(Collectors.toMap(Article::getDenomination, a -> a));

            List<ManufacturedArticleDetail> details = new ArrayList<>();
            double totalCost = 0.0;

            for (Map.Entry<String, Integer> entry : ingredientMap.entrySet()) {
                Article article = articleMap.get(entry.getKey());
                if (article == null) {
                    throw new RuntimeException("No existe el artículo: " + entry.getKey());
                }

                double cost = article.getBuyingPrice() * entry.getValue();
                totalCost += cost;

                details.add(ManufacturedArticleDetail.builder()
                    .article(article)
                    .quantity(entry.getValue())
                    .manufacturedArticle(null) // set later
                    .build());
            }

            double finalPrice = totalCost * 1.25; // 50% profit margin

            ManufacturedArticle manufacturedArticle = ManufacturedArticle.builder()
                .name(name)
                .description(description)
                .price(finalPrice)
                .isAvailable(true)
                .estimatedTimeMinutes(time)
                .stock(50)
                .category(category)
                .build();

            for (ManufacturedArticleDetail detail : details) {
                detail.setManufacturedArticle(manufacturedArticle);
            }

            manufacturedArticle.setManufacturedArticleDetail(details);

            InventoryImage inventoryImage = InventoryImage.builder()
                .imageData(loadImageBytes(imagePath))
                .build();

            manufacturedArticle.setManufacInventoryImage(inventoryImage);

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
    
        Employee admin = new Employee();
        admin.setEmployeeRole(Role.ADMIN);
        admin.setSalary(30000.0);
        admin.setShift(Shift.MORNING);
        admin.setUsername("alice_admin");
        admin.setPassword("password123");
        admin.setName("Alice");
        admin.setLastName("Anderson");
        admin.setPhoneNumber("1111111111");
        admin.setEmail("alice.admin@example.com");
        admin.setBirthDate(java.sql.Date.valueOf("1990-05-15"));
        employees.add(admin);
    
        Employee cashier = new Employee();
        cashier.setEmployeeRole(Role.CASHIER);
        cashier.setSalary(28000.0);
        cashier.setShift(Shift.EVENING);
        cashier.setUsername("bob_cashier");
        cashier.setPassword("password123");
        cashier.setName("Bob");
        cashier.setLastName("Brown");
        cashier.setPhoneNumber("2222222222");
        cashier.setEmail("bob.cashier@example.com");
        cashier.setBirthDate(java.sql.Date.valueOf("1988-11-23"));
        employees.add(cashier);
    
        Employee chef1 = new Employee();
        chef1.setEmployeeRole(Role.CHEF);
        chef1.setSalary(29000.0);
        chef1.setShift(Shift.NIGHT);
        chef1.setUsername("carla_chef");
        chef1.setPassword("password123");
        chef1.setName("Carla");
        chef1.setLastName("Cook");
        chef1.setPhoneNumber("3333333333");
        chef1.setEmail("carla.chef@example.com");
        chef1.setBirthDate(java.sql.Date.valueOf("1992-03-08"));
        employees.add(chef1);
    
        Employee driver = new Employee();
        driver.setEmployeeRole(Role.DRIVER);
        driver.setSalary(27000.0);
        driver.setShift(Shift.EVENING);
        driver.setUsername("david_driver");
        driver.setPassword("password123");
        driver.setName("David");
        driver.setLastName("Doyle");
        driver.setPhoneNumber("4444444444");
        driver.setEmail("david.driver@example.com");
        driver.setBirthDate(java.sql.Date.valueOf("1985-07-19"));
        employees.add(driver);
    
        Employee chef2 = new Employee();
        chef2.setEmployeeRole(Role.CHEF);
        chef2.setSalary(29500.0);
        chef2.setShift(Shift.MORNING);
        chef2.setUsername("emma_chef");
        chef2.setPassword("password123");
        chef2.setName("Emma");
        chef2.setLastName("Evans");
        chef2.setPhoneNumber("5555555555");
        chef2.setEmail("emma.chef@example.com");
        chef2.setBirthDate(java.sql.Date.valueOf("1994-01-30"));
        employees.add(chef2);
    
        employeeRepository.saveAll(employees);
        return "Initialized " + employees.size() + " employees.";
    }
    

    @Transactional
    public String initializeUsers() {
        try {
            List<Client> users = new ArrayList<>();
            byte[] imageBytes = loadImageBytes("images/users/default_pfp.png");

            users.add(createClient("Juan", "Garcia", "15487524168", "1994-01-30", "jgarcia@mail.com", "JGarcia", "JGarcia123", "Adolfo Calle", 161, "M5521", 1L, imageBytes));
            users.add(createClient("Lucia", "Martinez", "15487986512", "1990-06-15", "lucia.m@mail.com", "LMartinez", "Lucia2024", "San Martin", 832, "M5500", 2L, imageBytes));
            users.add(createClient("Carlos", "Perez", "15483321478", "1988-12-20", "c.perez@mail.com", "CarlosP", "Perez88!", "Godoy Cruz", 121, "M5530", 3L, imageBytes));
            users.add(createClient("Ana", "Lopez", "15481245789", "1995-03-10", "ana.lopez@mail.com", "AnaLo", "AnaSecure1", "Belgrano", 754, "M5502", 4L, imageBytes));
            users.add(createClient("Diego", "Fernandez", "15487654231", "1992-07-25", "diego.f@mail.com", "DFernandez", "DiegoF22", "España", 903, "M5501", 5L, imageBytes));
            users.add(createClient("Sofia", "Romero", "15489963214", "1996-09-02", "sofia.r@mail.com", "SofiaR", "SofPass94", "Las Heras", 425, "M5528", 6L, imageBytes));
            users.add(createClient("Martin", "Alvarez", "15488751234", "1991-11-11", "martin.al@mail.com", "MartinA", "Marty123", "Patricias Mendocinas", 200, "M5519", 7L, imageBytes));
            users.add(createClient("Valentina", "Diaz", "15486674839", "1993-04-17", "val.diaz@mail.com", "ValDiaz", "ValVal44", "25 de Mayo", 578, "M5517", 8L, imageBytes));
            users.add(createClient("Tomas", "Sanchez", "15484562178", "1989-10-09", "tomas.s@mail.com", "TomasS", "TomasS!", "Mitre", 342, "M5503", 9L, imageBytes));
            users.add(createClient("Camila", "Torres", "15484321876", "1997-08-28", "camila.t@mail.com", "CamilaT", "CamT2025", "Necochea", 129, "M5505", 10L, imageBytes));
            users.add(createClient("Federico", "Mendez", "15487231645", "1990-02-02", "fede.m@mail.com", "FedeM", "FedePass", "Pedro Molina", 743, "M5506", 1L, imageBytes));
            users.add(createClient("Julieta", "Herrera", "15486123547", "1994-05-21", "julieta.h@mail.com", "JulietaH", "Julieta90", "Olascoaga", 398, "M5508", 2L, imageBytes));
            users.add(createClient("Sebastian", "Gomez", "15482314567", "1987-03-03", "sebas.g@mail.com", "SebasG", "Sebas@123", "Sarmiento", 856, "M5509", 3L, imageBytes));
            users.add(createClient("Agustina", "Vega", "15485976123", "1992-06-06", "agus.v@mail.com", "AgusV", "Agustina1", "Paso de los Andes", 231, "M5510", 4L, imageBytes));
            users.add(createClient("Bruno", "Silva", "15487765432", "1995-12-12", "bruno.s@mail.com", "BrunoS", "BrunoPwd", "Boulogne Sur Mer", 657, "M5511", 5L, imageBytes));
    
            clientRepository.saveAll(users);
            return "Users Initialized";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize users", e);
        }
    }
    
    private Client createClient(String name, String lastName, String phone, String birth, String email, String username, String password,
                                String street, int number, String zip, Long locationId, byte[] imageBytes) {
        Client client = new Client();
        client.setName(name);
        client.setLastName(lastName);
        client.setPhoneNumber(phone);
        client.setBirthDate(java.sql.Date.valueOf(birth));
        client.setEmail(email);
        client.setUsername(username);
        client.setPassword(password);
        client.setUserImage(UserImage.builder().imageData(imageBytes).build());
    
        Location location = locationRepository.findById(locationId)
            .orElseThrow(() -> new RuntimeException("Location not found: " + locationId));
        
        Domicile domicile = Domicile.builder()
                .street(street)
                .zipCode(zip)
                .number(number)
                .location(location)
                .client(client)
                .build();
    
        client.setDomiciles(List.of(domicile));
        return client;
    }

    @Transactional
    public String initializeSales() {
        try {
            List<Sale> sales = new ArrayList<>();
    
            sales.add(buildSale(
                "Oferta de Verano", SaleType.SUMMERSALE, 0.8 ,"Descuento por temporada de calor.",
                LocalDate.of(2025, Month.DECEMBER, 21), LocalDate.of(2026, Month.MARCH, 20),
                "images/sale/summer_sale.png",
                List.of(
                    detail(articleRepository.findById(42L).orElseThrow(), 1),
                    detail(manufacturedArticleRepository.findById(2L).orElseThrow(), 1),
                    detail(manufacturedArticleRepository.findById(3L).orElseThrow(), 1)
                )
            ));
    
            sales.add(buildSale(
                "Oferta de Invierno", SaleType.WINTERSALE, 0.7, "Descuento por temporada de frio.",
                LocalDate.of(2025, Month.JUNE, 20), LocalDate.of(2025, Month.SEPTEMBER, 22),
                "images/sale/winter_sale.png",
                List.of(
                    detail(articleRepository.findById(43L).orElseThrow(), 1),
                    detail(manufacturedArticleRepository.findById(9L).orElseThrow(), 1),
                    detail(manufacturedArticleRepository.findById(11L).orElseThrow(), 1)
                )
            ));
    
            sales.add(buildSale(
                "Oferta de Primavera", SaleType.SPRINGSALE, 0.75,"Descuento por epoca primaveral.",
                LocalDate.of(2025, Month.SEPTEMBER, 23), LocalDate.of(2025, Month.DECEMBER, 22),
                "images/sale/spring_sale.png",
                List.of(
                    detail(articleRepository.findById(47L).orElseThrow(), 1),
                    detail(manufacturedArticleRepository.findById(10L).orElseThrow(), 1),
                    detail(manufacturedArticleRepository.findById(15L).orElseThrow(), 1)
                )
            ));
    
            sales.add(buildSale(
                "Oferta de Otoño", SaleType.FALLSALE, 0.85, "Descuento por epoca otoñal.",
                LocalDate.of(2026, Month.MARCH, 20), LocalDate.of(2026, Month.JUNE, 20),
                "images/sale/fall_sale.png",
                List.of(
                    detail(articleRepository.findById(44L).orElseThrow(), 1),
                    detail(manufacturedArticleRepository.findById(5L).orElseThrow(), 1),
                    detail(manufacturedArticleRepository.findById(11L).orElseThrow(), 1)
                )
            ));
    
            sales.add(buildSale(
                "Oferta de Navidad", SaleType.CHRISTMASSALE, 0.75, "Descuento por Navidad.",
                LocalDate.of(2025, Month.DECEMBER, 1), LocalDate.of(2025, Month.DECEMBER, 30),
                "images/sale/christmass_sale.png",
                List.of(
                    detail(articleRepository.findById(56L).orElseThrow(), 2),
                    detail(manufacturedArticleRepository.findById(12L).orElseThrow(), 1),
                    detail(manufacturedArticleRepository.findById(1L).orElseThrow(), 1)
                )
            ));
    
            saleRepository.saveAll(sales);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize sales", e);
        }
    
        return "Sales Initialized";
    }
    
    

    private Sale buildSale(String denomination, SaleType type, Double saleDiscount, String description,
    LocalDate start, LocalDate end, String imagePath,
    List<SaleDetail> details) throws IOException {
InventoryImage image = InventoryImage.builder()
.imageData(loadImageBytes(imagePath))
.build();

double fullPrice = details.stream().mapToDouble(detail -> {
if (detail.getArticle() != null) {
return detail.getArticle().getBuyingPrice() * detail.getQuantity();
} else if (detail.getManufacturedArticle() != null) {
return detail.getManufacturedArticle().getPrice() * detail.getQuantity();
}
return 0.0;
}).sum();

double discountedPrice = fullPrice * saleDiscount;

Sale sale = Sale.builder()
.denomination(denomination)
.startDate(start)
.endDate(end)
.saleDiscount(saleDiscount)
.startTime(LocalTime.of(12, 0))
.endTime(LocalTime.of(12, 0))
.saleDescription(description)
.salePrice(discountedPrice)
.isActive(true)
.saleType(type)
.inventoryImage(image)
.saleDetails(new ArrayList<>()) // set below
.build();

details.forEach(d -> d.setSale(sale));
sale.setSaleDetails(details);

return sale;
}
    
    private SaleDetail detail(Article a, int qty) {
        return SaleDetail.builder()
            .article(a)
            .quantity(qty)
            .build();
    }
    
    private SaleDetail detail(ManufacturedArticle ma, int qty) {
        return SaleDetail.builder()
            .manufacturedArticle(ma)
            .quantity(qty)
            .build();
    }

    public void initializeOrders() throws IOException {

        byte[] imageBytes = loadImageBytes("images/users/default_pfp.png");

         List<Client> clients = clientRepository.findAll();
        List<ManufacturedArticle> mArticles = manufacturedArticleRepository.findAll();

        List<Order> orders = new ArrayList<>();
        LocalDate baseDate = LocalDate.of(2025, 6, 1);
        OrderState[] states = OrderState.values();
        for (int i = 0; i < 30; i++) {
            Order order = new Order();

            order.setEstimatedFinishTime(LocalTime.of(12 + (i % 6), (i * 5) % 60));
            order.setTotal(5000 + (i * 150));
            order.setTotalCost(4000 + (i * 100));
            order.setOrderDate(baseDate.plusDays(i));
            order.setOrderState(states[i % states.length]);
            order.setOrderType(i % 3 == 0 ? OrderType.TAKEAWAY :
                    i % 3 == 1 ? OrderType.DELIVERY :
                            OrderType.ON_SITE);
            order.setPayMethod(i % 2 == 0 ? PayMethod.MERCADOPAGO : PayMethod.CASH);
            order.setDirection("Calle Falsa " + (100 + i));
            order.setSubsidiaryId((i % 5) + 1);

            order.setClient(clients.get(i % clients.size()));

            ManufacturedArticle mArticle = mArticles.get(i % mArticles.size());
            OrderDetails detail = new OrderDetails();
            detail.setQuantity(1 + i % 4);
            detail.setSubTotal(1000.0 + (i * 50));
            detail.setOrder(order);
            detail.setManufacturedArticle(mArticle);

            order.getOrderDetails().add(detail);

            orders.add(order);
        }
        orderRepository.saveAll(orders);

    }
}

