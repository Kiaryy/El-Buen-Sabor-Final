package supercell.ElBuenSabor.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import supercell.ElBuenSabor.Models.*;
import supercell.ElBuenSabor.Models.enums.OrderState;
import supercell.ElBuenSabor.Models.enums.OrderType;
import supercell.ElBuenSabor.Models.payload.*;
import supercell.ElBuenSabor.repository.*;
import supercell.ElBuenSabor.service.OrderService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final BillRepository billRepository;
    private final ManufacturedArticleRepository manufacturedArticleRepository;
    private final PayMethodRepository payMethodRepository;
    private final ArticleRepository articleRepository;
    private final ManufacturedArticleDetailRepository articleDetailRepository;
    private final SaleRepository saleRepository;

    @Transactional
    @Override
    public BillResponseDTO createOrder(OrderRequestDTO request) {
        // --- Validate client ---
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    
        // --- Estimate finish time ---
        LocalTime estimatedTime = request.getEstimatedFinishTime();
        if (!request.isTakeAway()) {
            estimatedTime = estimatedTime.plusMinutes(10);
        }
    
        // --- Build order ---
        Order order = new Order();
        order.setEstimatedFinishTime(estimatedTime);
        order.setTotal(request.getTotal());
        order.setTotalCost(request.getTotalCost());
        order.setOrderDate(request.getOrderDate());
        order.setClient(client);
        order.setOrderState(request.getOrderState());
        order.setOrderType(Optional.ofNullable(request.getOrderType()).orElse(OrderType.ON_SITE));
        order.setPayMethod(request.getPayMethod());
        order.setSubsidiaryId(request.getSubsidiaryId());
        order.setDirection(request.getDirection());
    
        List<OrderDetails> orderDetailsList = new ArrayList<>();
    
        // --- Just register manufactured articles, no stock change here ---
        if (request.getOrderDetails() != null) {
            for (OrderRequestDTO.OrderDetailDTO detailDTO : request.getOrderDetails()) {
                ManufacturedArticle mArticle = manufacturedArticleRepository.findById(detailDTO.getManufacturedArticleId())
                        .orElseThrow(() -> new RuntimeException("Artículo manufacturado no encontrado"));
    
                OrderDetails detail = new OrderDetails();
                detail.setOrder(order);
                detail.setManufacturedArticle(mArticle);
                detail.setQuantity(detailDTO.getQuantity());
                detail.setSubTotal(detailDTO.getSubTotal());
                orderDetailsList.add(detail);
            }
        }
    
        // --- Just register articles (forSale), no stock change here ---
        if (request.getArticleDetails() != null) {
            for (OrderRequestDTO.ArticleDetailDTO detailDTO : request.getArticleDetails()) {
                Article article = articleRepository.findById(detailDTO.articleId())
                        .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));
    
                if (!article.isForSale()) {
                    throw new RuntimeException("Artículo no disponible para venta");
                }
    
                OrderDetails detail = new OrderDetails();
                detail.setOrder(order);
                detail.setArticle(article);
                detail.setQuantity(detailDTO.quantity());
                detail.setSubTotal(detailDTO.subTotal());
                orderDetailsList.add(detail);
            }
        }
    
        // --- Just register sales (promotions), no stock change here ---
        if (request.getSalesDetails() != null) {
            for (OrderRequestDTO.SalesDTO saleDTO : request.getSalesDetails()) {
                Sale sale = saleRepository.findById(saleDTO.getSaleID())
                        .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    
                OrderDetails detail = new OrderDetails();
                detail.setOrder(order);
                detail.setSale(sale);
                detail.setQuantity(saleDTO.getQuantity());
                detail.setSubTotal(saleDTO.getSubTotal());
                orderDetailsList.add(detail);
            }
        }
    
        order.setOrderDetails(orderDetailsList);
        orderRepository.save(order);
    
        // --- Create bill ---
        Bill bill = new Bill();
        bill.setOrder(order);
        bill.setBillingDate(LocalDate.now());
        bill.setPayment(request.getPayMethod());
        bill.setMpMerchantOrderID(null);
        bill.setMpPreferenceID(null);
        bill.setTotalSale(order.getTotal());
    
        bill = billRepository.save(bill);
        return toDto(bill);
    }

    


    @Override
    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDTO> responseList = new ArrayList<>();
    
        for (Order order : orders) {
            // Force initialize sale details
            order.getOrderDetails().forEach(detail -> {
                if (detail.getSale() != null) {
                    detail.getSale().getSaleDetails().size();
                }
            });
    
            // Build articles list
            List<ArticleDTO> articleDTOs = order.getOrderDetails().stream()
                    .filter(d -> d.getArticle() != null)
                    .map(d -> new ArticleDTO(
                            d.getArticle().getDenomination(),
                            d.getArticle().getCurrentStock(),
                            d.getArticle().getMaxStock(),
                            d.getArticle().getBuyingPrice(),
                            d.getArticle().getMeasuringUnit().getIDMeasuringUnit(),
                            d.getArticle().getCategory().getIDCategory(),
                            d.getArticle().getInventoryImage() != null
                                    ? new InventoryImageDTO(d.getArticle().getInventoryImage().getImageData())
                                    : null,
                            d.getArticle().isForSale(),
                            d.getQuantity(),
                            d.getArticle().isEnabled()
                    ))
                    .toList();
    
            // Build manufactured articles list
            List<ProductsOrderedDto> manufacturedArticles = order.getOrderDetails().stream()
                    .filter(d -> d.getManufacturedArticle() != null)
                    .map(d -> mapToManufacturedArticleDTO(d.getManufacturedArticle(), d.getQuantity()))
                    .toList();
    
            // Build sales list
            List<SaleResponseDTO> salesDTOs = order.getOrderDetails().stream()
                    .filter(d -> d.getSale() != null)
                    .map(d -> {
                        Sale sale = d.getSale();
                        List<ArticleDTO> saleArticles = sale.getSaleDetails().stream()
                                .map(sd -> {
                                    if (sd.getArticle() == null) return null;
                                    Article a = sd.getArticle();
                                    return new ArticleDTO(
                                            a.getDenomination(),
                                            a.getCurrentStock(),
                                            a.getMaxStock(),
                                            a.getBuyingPrice(),
                                            a.getMeasuringUnit().getIDMeasuringUnit(),
                                            a.getCategory().getIDCategory(),
                                            null,
                                            a.isForSale(),
                                            sd.getQuantity(),
                                            a.isEnabled()   // ✅ fixed
                                    );
                                })
                                .filter(Objects::nonNull)
                                .toList();
    
                        return new SaleResponseDTO(
                                Math.toIntExact(sale.getIDSale()),
                                sale.getDenomination(),
                                sale.getStartDate(),
                                sale.getEndDate(),
                                sale.getSaleDescription(),
                                saleArticles,
                                sale.getSalePrice() != null ? sale.getSalePrice() : 0.0,
                                d.getQuantity()
                        );
                    })
                    .toList();
    
            // Build client DTO
            Client client = order.getClient();
            ClientDto clientDto = clientToDto(client);
    
            // Build order response
            OrderResponseDTO dto = new OrderResponseDTO();
            dto.setId(order.getId());
            dto.setEstimatedFinishTime(order.getEstimatedFinishTime());
            dto.setTotal(order.getTotal());
            dto.setTotalCost(order.getTotalCost());
            dto.setOrderDate(order.getOrderDate());
            dto.setOrderState(order.getOrderState().toString());
            dto.setOrderType(order.getOrderType().toString());
            dto.setPayMethod(order.getPayMethod().toString());
            dto.setClient(clientDto);
            dto.setDirectionToSend(order.getDirection());
            dto.setSubsidiaryId(order.getSubsidiaryId());
            dto.setManufacturedArticles(manufacturedArticles);
            dto.setOrderedArticles(articleDTOs);
            dto.setSales(salesDTOs);
    
            responseList.add(dto);
        }
    
        return responseList;
    }
    
    
    


    private List<ProductsOrderedDto> getManufacturedArticleDetails(Order order) {

        return order.getOrderDetails().stream()
                .filter(detail -> detail.getManufacturedArticle() != null)
                .map(detail -> {
                    ManufacturedArticle mArticle = detail.getManufacturedArticle();

                    List<ArticleDTO> detailDTOs = mArticle.getManufacturedArticleDetail().stream()
                            .map(d -> {
                                return new ArticleDTO(
                                                d.getArticle().getDenomination(),
                                                d.getArticle().getCurrentStock(),
                                                d.getArticle().getMaxStock(),
                                                null,
                                                null,
                                                d.getArticle().getCategory().getIDCategory(),
                                                null,
                                                d.getArticle().isForSale(),
                                                0,
                                                d.getArticle().isEnabled()
                                        );
                            }
                            )
                            .toList();

                    InventoryImageDTO imageDTO = new InventoryImageDTO(
                            mArticle.getManufacInventoryImage().getImageData()
                    );

                    return new ProductsOrderedDto(
                            mArticle.getIDManufacturedArticle(),
                            mArticle.getName(),
                            mArticle.getDescription(),
                            mArticle.getPrice(),
                            mArticle.getEstimatedTimeMinutes(),
                            mArticle.isAvailable(),
                            detail.getQuantity(),
                            detailDTOs,
                            null,
                            mArticle.getCategory().getIDCategory()
                    );
                })
                .toList();
    }

    public OrderResponseDTO getOrderById(Integer id) {
        Order orders = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
    
        // Articles with quantity
        List<ArticleDTO> articleDTOs = orders.getOrderDetails().stream()
                .filter(detail -> detail.getArticle() != null)
                .map(detail -> {
                    Article article = detail.getArticle();
                    return new ArticleDTO(
                            article.getDenomination(),
                            article.getCurrentStock(),
                            article.getMaxStock(),
                            article.getBuyingPrice(),
                            article.getMeasuringUnit().getIDMeasuringUnit(),
                            article.getCategory().getIDCategory(),
                            new InventoryImageDTO(article.getInventoryImage().getImageData()),
                            article.isForSale(),
                            detail.getQuantity(),
                            article.isEnabled()   
                    );
                }).toList();
    
        // Manufactured articles (already include quantity)
        List<ProductsOrderedDto> manufacturedArticles = orders.getOrderDetails().stream()
                .filter(detail -> detail.getManufacturedArticle() != null)
                .map(detail -> mapToManufacturedArticleDTO(detail.getManufacturedArticle(), detail.getQuantity()))
                .toList();
    
        // Sales with quantity
        List<SaleResponseDTO> saleDtos = orders.getOrderDetails().stream()
                .filter(detail -> detail.getSale() != null)
                .map(detail -> {
                    Sale sale = detail.getSale();
                    List<ArticleDTO> saleArticles = sale.getSaleDetails().stream()
                            .map(sd -> {
                                Article a = sd.getArticle();
                                if (a == null) return null;
                                return new ArticleDTO(
                                        a.getDenomination(),
                                        a.getCurrentStock(),
                                        a.getMaxStock(),
                                        a.getBuyingPrice(),
                                        a.getMeasuringUnit().getIDMeasuringUnit(),
                                        a.getCategory().getIDCategory(),
                                        null,
                                        a.isForSale(),
                                        sd.getQuantity(),
                                        a.isEnabled()  
                                );
                            })
                            .filter(Objects::nonNull)
                            .toList();
    
                    return new SaleResponseDTO(
                            Math.toIntExact(sale.getIDSale()),
                            sale.getDenomination(),
                            sale.getStartDate(),
                            sale.getEndDate(),
                            sale.getSaleDescription(),
                            saleArticles,
                            sale.getSalePrice(),
                            detail.getQuantity()   
                    );
                })
                .toList();
    
        return OrderResponseDTO.builder()
                .id(orders.getId())
                .estimatedFinishTime(orders.getEstimatedFinishTime())
                .total(orders.getTotal())
                .totalCost(orders.getTotalCost())
                .orderDate(orders.getOrderDate())
                .orderState(orders.getOrderState().toString())
                .orderType(orders.getOrderType().toString())
                .payMethod(orders.getPayMethod().toString())
                .subsidiaryId(orders.getSubsidiaryId())
                .client(OrderServiceImpl.clientToDto(orders.getClient()))
                .manufacturedArticles(manufacturedArticles)
                .orderedArticles(articleDTOs)
                .sales(saleDtos)
                .directionToSend(orders.getDirection())
                .build();
    }
    

    @Override
    @Transactional
    public OrderResponseDTO changeOrderStatus(Integer orderId, OrderState newState) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
    
        OrderState oldState = order.getOrderState();
    
        // --- Move to PREPARING: discount stock ---
        if (newState == OrderState.PREPARING && oldState == OrderState.PENDING) {
            for (OrderDetails detail : order.getOrderDetails()) {
                // Case A: Direct article (drink/etc)
                if (detail.getArticle() != null && detail.getArticle().isForSale()) {
                    Article article = detail.getArticle();
                    if (article.getCurrentStock() < detail.getQuantity()) {
                        throw new RuntimeException("Stock insuficiente de " + article.getDenomination());
                    }
                    article.setCurrentStock(article.getCurrentStock() - detail.getQuantity());
                    articleRepository.save(article);
                }
    
                // Case B: Manufactured article
                if (detail.getManufacturedArticle() != null) {
                    ManufacturedArticle mArticle = detail.getManufacturedArticle();
                    int qty = detail.getQuantity();
    
                    for (ManufacturedArticleDetail mad : mArticle.getManufacturedArticleDetail()) {
                        Article ingredient = mad.getArticle();
                        int required = mad.getQuantity() * qty;
                        if (ingredient.getCurrentStock() < required) {
                            throw new RuntimeException("Stock insuficiente del insumo: " + ingredient.getDenomination());
                        }
                        ingredient.setCurrentStock(ingredient.getCurrentStock() - required);
                        articleRepository.save(ingredient);
                    }
    
                    // int stock = mArticle.getStock() != null ? mArticle.getStock() : 0;
                    // if (stock < qty) {
                    //     throw new RuntimeException("Stock insuficiente del manufacturado: " + mArticle.getName());
                    // }
                    // mArticle.setStock(stock - qty);
                    manufacturedArticleRepository.save(mArticle);
                }
    
                // Case C: Sale (promotion)
                if (detail.getSale() != null) {
                    int saleQty = detail.getQuantity();
                    for (SaleDetail saleDetail : detail.getSale().getSaleDetails()) {
                        if (saleDetail.getArticle() != null) {
                            Article article = saleDetail.getArticle();
                            int required = saleDetail.getQuantity() * saleQty;
                            if (article.getCurrentStock() < required) {
                                throw new RuntimeException("Stock insuficiente de " + article.getDenomination());
                            }
                            article.setCurrentStock(article.getCurrentStock() - required);
                            articleRepository.save(article);
                        }
                        if (saleDetail.getManufacturedArticle() != null) {
                            ManufacturedArticle mArticle = saleDetail.getManufacturedArticle();
                            int requiredMfg = saleDetail.getQuantity() * saleQty;
    
                            for (ManufacturedArticleDetail mad : mArticle.getManufacturedArticleDetail()) {
                                Article ingredient = mad.getArticle();
                                int required = mad.getQuantity() * requiredMfg;
                                if (ingredient.getCurrentStock() < required) {
                                    throw new RuntimeException("Stock insuficiente del insumo: " + ingredient.getDenomination());
                                }
                                ingredient.setCurrentStock(ingredient.getCurrentStock() - required);
                                articleRepository.save(ingredient);
                            }
    
                            // int stock = mArticle.getStock() != null ? mArticle.getStock() : 0;
                            // if (stock < requiredMfg) {
                            //     throw new RuntimeException("Stock insuficiente del manufacturado: " + mArticle.getName());
                            // }
                            // mArticle.setStock(stock - requiredMfg);
                            manufacturedArticleRepository.save(mArticle);
                        }
                    }
                }
            }
        }
    
        // --- Cancelled: restore stock (ONLY if it was PREPARING) ---
        if (newState == OrderState.CANCELED && oldState == OrderState.PREPARING) {
            for (OrderDetails detail : order.getOrderDetails()) {
                if (detail.getArticle() != null && detail.getArticle().isForSale()) {
                    Article article = detail.getArticle();
                    article.setCurrentStock(article.getCurrentStock() + detail.getQuantity());
                    articleRepository.save(article);
                }
                if (detail.getSale() != null) {
                    for (SaleDetail saleDetail : detail.getSale().getSaleDetails()) {
                        if (saleDetail.getArticle() != null && saleDetail.getArticle().isForSale()) {
                            Article article = saleDetail.getArticle();
                            int restore = saleDetail.getQuantity() * detail.getQuantity();
                            article.setCurrentStock(article.getCurrentStock() + restore);
                            articleRepository.save(article);
                        }
                    }
                }
            }
        }
    
        order.setOrderState(newState);
        orderRepository.save(order);
    
        return OrderResponseDTO.builder()
                .id(order.getId())
                .estimatedFinishTime(order.getEstimatedFinishTime())
                .total(order.getTotal())
                .totalCost(order.getTotalCost())
                .orderDate(order.getOrderDate())
                .orderState(order.getOrderState().toString())
                .orderType(order.getOrderType().toString())
                .payMethod(order.getPayMethod().toString())
                .subsidiaryId(order.getSubsidiaryId())
                .client(OrderServiceImpl.clientToDto(order.getClient()))
                .directionToSend(order.getDirection())
                .build();
    }
    
    
    

    @Override
    public List<ProductsOrderedDto> getOrderedProductByUserId(Long userId) {
        List<Order> orderList = orderRepository.findOrderByClient(userId);
    
        if (orderList.isEmpty()) {
            throw new RuntimeException("No hay órdenes asociadas al cliente con id " + userId);
        }
    
        List<ProductsOrderedDto> dtoList = new ArrayList<>();
    
        for (Order order : orderList) {
            for (OrderDetails detail : order.getOrderDetails()) {
                ManufacturedArticle mArticle = detail.getManufacturedArticle();
                Article article = detail.getArticle();
                Sale sale = detail.getSale();
    
                // Manufactured article
                if (mArticle != null) {
                    ProductsOrderedDto dto = new ProductsOrderedDto(
                            mArticle.getIDManufacturedArticle(),
                            mArticle.getName(),
                            mArticle.getDescription(),
                            mArticle.getPrice(),
                            mArticle.getEstimatedTimeMinutes(),
                            mArticle.isAvailable(),
                            detail.getQuantity(),
                            null,
                            null,
                            mArticle.getCategory().getIDCategory()
                    );
                    dtoList.add(dto);
                }
    
                // Single article (e.g. beverage)
                if (article != null) {
                    ArticleDTO articleDTO = new ArticleDTO(
                            article.getDenomination(),
                            article.getCurrentStock(),
                            article.getMaxStock(),
                            article.getBuyingPrice(),
                            article.getMeasuringUnit().getIDMeasuringUnit(),
                            article.getCategory().getIDCategory(),
                            new InventoryImageDTO(
                                    article.getInventoryImage() != null ?
                                            article.getInventoryImage().getImageData() :
                                            null),
                            article.isForSale(),
                            detail.getQuantity(),
                            article.isEnabled()
                    );
    
                    ProductsOrderedDto dto = new ProductsOrderedDto(
                            article.getIDArticle(),
                            article.getDenomination(),
                            "",
                            article.getBuyingPrice(),
                            0,
                            article.isForSale(),
                            detail.getQuantity(),
                            List.of(articleDTO),
                            null,
                            article.getCategory().getIDCategory()
                    );
                    dtoList.add(dto);
                }
    
                // Sale (promotion)
                if (sale != null) {
                    List<ArticleDTO> saleArticles = sale.getSaleDetails().stream()
                            .map(sd -> {
                                Article a = sd.getArticle();
                                if (a == null) return null;
                                return new ArticleDTO(
                                        a.getDenomination(),
                                        a.getCurrentStock(),
                                        a.getMaxStock(),
                                        a.getBuyingPrice(),
                                        a.getMeasuringUnit().getIDMeasuringUnit(),
                                        a.getCategory().getIDCategory(),
                                        null,
                                        a.isForSale(),
                                        sd.getQuantity(),
                                        sd.getArticle().isEnabled()
                                );
                            })
                            .filter(Objects::nonNull)
                            .toList();
    
                    ProductsOrderedDto dto = new ProductsOrderedDto(
                            sale.getIDSale(),            // ✅ keep as Long
                            sale.getDenomination(),
                            sale.getSaleDescription(),
                            sale.getSalePrice(),
                            0,
                            true,                        // sales are always sellable
                            detail.getQuantity(),        // how many times the sale was ordered
                            saleArticles,
                            null,
                            null
                    );
                    dtoList.add(dto);
                }
            }
        }
    
        return dtoList;
    }
    

    public static BillResponseDTO toDto(Bill bill) {
        if (bill == null) {
            return null;
        }

        BillResponseDTO dto = new BillResponseDTO();
        dto.setId(bill.getId());
        dto.setBillingDate(bill.getBillingDate());
        dto.setPaymentMethod(bill.getPayment());
        dto.setMpMerchantOrderID(bill.getMpMerchantOrderID());
        dto.setMpPreferenceID(bill.getMpPreferenceID());
        dto.setTotalSale(bill.getTotalSale());
        if (bill.getOrder() != null) {
            dto.setOrderId(Long.valueOf(bill.getOrder().getId()));
        }

        return dto;
    }

    public static ClientDto clientToDto(Client client){
        List<DomicileDTO> domicileDTOS = client.getDomiciles().stream().map((domicile -> {
            DomicileDTO domicileDTO = new DomicileDTO( domicile.getStreet(), domicile.getZipCode(),domicile.getNumber(),null );
            return domicileDTO;
        })).toList();

        ClientDto dto = new ClientDto();
        dto.setClientId(client.getId());
        dto.setUsername(client.getUsername());
        dto.setFirstName(client.getName());
        dto.setLastName(client.getLastName());
        dto.setPhoneNumber(client.getPhoneNumber());
        dto.setBirthDate(client.getBirthDate());
        dto.setEmail(client.getEmail());
        dto.setDomiciles(domicileDTOS);
        return dto;
    }
    private ProductsOrderedDto mapToManufacturedArticleDTO(ManufacturedArticle article, Integer quantityOrdered) {
        InventoryImageDTO imageDTO = new InventoryImageDTO(
                article.getManufacInventoryImage().getImageData()
        );

        return new ProductsOrderedDto(
                article.getIDManufacturedArticle(),
                article.getName(),
                article.getDescription(),
                article.getPrice(),
                article.getEstimatedTimeMinutes(),
                article.isAvailable(),
                quantityOrdered,
                null,
                null,
                article.getCategory().getIDCategory()
        );
    }

    public OrderWithPromosDTO getOrderWithProductsAndPromos(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        List<ProductsOrderedDto> products = order.getOrderDetails().stream()
                .map(detail -> {
                    ManufacturedArticle article = detail.getManufacturedArticle();
                    return new ProductsOrderedDto(
                            article.getIDManufacturedArticle(),
                            article.getName(),
                            article.getDescription(),
                            article.getPrice(),
                            article.getEstimatedTimeMinutes(),
                            article.isAvailable(),
                            detail.getQuantity(),
                            article.getManufacturedArticleDetail().stream()
                                    .map(d -> {
                                        return  new ArticleDTO(
                                                d.getArticle().getDenomination(),
                                                d.getArticle().getCurrentStock(),
                                                d.getArticle().getMaxStock(),
                                                d.getArticle().getBuyingPrice(),
                                                d.getArticle().getMeasuringUnit().getIDMeasuringUnit(),
                                                d.getArticle().getCategory().getIDCategory(),
                                                null,
                                                d.getArticle().isForSale(),
                                                0,
                                                d.getArticle().isEnabled()
                                        );
                                    })
                                    .toList(),
                            new InventoryImageDTO(article.getManufacInventoryImage().getImageData()),
                            article.getCategory().getIDCategory()
                    );
                }).toList();

        List<ManufacturedArticle> orderedArticles = order.getOrderDetails().stream()
                .map(OrderDetails::getManufacturedArticle)
                .distinct()
                .toList();

        List<Sale> sales = saleRepository.findSalesByManufacturedArticles(orderedArticles);

        List<SaleResponseDTO> saleDtos = sales.stream()
                .map(sale -> {
                    List<ArticleDTO> articleDTOs = sale.getSaleDetails().stream()
                            .map(detail -> {
                                Article a = detail.getArticle();
                                if (a == null) {
                                    return null;
                                }
                                    return new ArticleDTO(
                                            a.getDenomination(),
                                            a.getCurrentStock(),
                                            a.getMaxStock(),
                                            a.getBuyingPrice(),
                                            a.getMeasuringUnit().getIDMeasuringUnit(),
                                            a.getCategory().getIDCategory(),
                                            null,
                                            a.isForSale(),
                                            detail.getQuantity(),
                                            a.isEnabled()
                                    );


                            })
                            .toList();

                    return new SaleResponseDTO(
                            Math.toIntExact(sale.getIDSale()),
                            sale.getDenomination(),
                            sale.getStartDate(),
                            sale.getEndDate(),
                            sale.getSaleDescription(),
                            articleDTOs,
                            sale.getSalePrice(),
                            0

                    );
                })
                .toList();

        return new OrderWithPromosDTO(
                order.getId(),
                order.getOrderDate(),
                order.getClient().getName() + " " + order.getClient().getLastName(),
                products,
                saleDtos
        );
    }

    @Override
    public OrderStatisticsDTO getOrderStatistics() {
        List<Order> orders = orderRepository.findAll();

        long totalOrders = orders.size();
        double totalRevenue = orders.stream().mapToDouble(Order::getTotal).sum();
        double totalCost = orders.stream().mapToDouble(Order::getTotalCost).sum();
        double profit = totalRevenue - totalCost;

        long canceledOrders = orders.stream().filter(o -> o.getOrderState() == OrderState.CANCELED).count();
        long pendingOrders = orders.stream().filter(o -> o.getOrderState() == OrderState.PENDING).count();
        long deliveredOrders = orders.stream().filter(o -> o.getOrderState() == OrderState.ARRIVED).count();

        Map<String, Long> ordersByType = orders.stream()
                .collect(Collectors.groupingBy(o -> o.getOrderType().name(), Collectors.counting()));

        OrderStatisticsDTO dto = new OrderStatisticsDTO();
        dto.setTotalOrders(totalOrders);
        dto.setTotalRevenue(totalRevenue);
        dto.setTotalCost(totalCost);
        dto.setProfit(profit);
        dto.setCanceledOrders(canceledOrders);
        dto.setPendingOrders(pendingOrders);
        dto.setDeliveredOrders(deliveredOrders);
        dto.setOrdersByType(ordersByType);

        return dto;
    }

}
