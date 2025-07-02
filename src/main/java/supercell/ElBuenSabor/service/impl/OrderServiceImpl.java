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

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if(client == null) {
            throw new RuntimeException("Cliente no encontrado");
        }
        LocalTime estimatedTime = request.getEstimatedFinishTime();

        if(!request.isTakeAway()){
            estimatedTime = estimatedTime.plusMinutes(10);
        }

        Order order = new Order();
        order.setEstimatedFinishTime(estimatedTime);
        order.setTotal(request.getTotal());
        order.setTotalCost(request.getTotalCost());
        order.setOrderDate(request.getOrderDate());
        order.setClient(client);
        order.setOrderState(request.getOrderState());
        switch (request.getOrderType()){
            case ON_SITE -> order.setOrderType(OrderType.ON_SITE);
            case DELIVERY -> order.setOrderType(OrderType.DELIVERY);
            case TAKEAWAY -> order.setOrderType(OrderType.TAKEAWAY);
            default -> order.setOrderType(OrderType.ON_SITE);
            }
        order.setPayMethod(request.getPayMethod());
        order.setSubsidiaryId(request.getSubsidiaryId());
        order.setDirection(request.getDirection());
        List<OrderDetails> orderDetailsList = new ArrayList<>();

        for (OrderRequestDTO.OrderDetailDTO detailDTO : request.getOrderDetails()) {
            ManufacturedArticle mArticle = manufacturedArticleRepository.findById(detailDTO.getManufacturedArticleId())
                    .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));

            int orderedQty = detailDTO.getQuantity();

            for (ManufacturedArticleDetail mad : mArticle.getManufacturedArticleDetail()) {
                Article article = mad.getArticle();

                if(mad.getQuantity() < orderedQty) {
                    throw new RuntimeException("Stock insuficiente de ingredientes del artículo: "+ mad.getManufacturedArticle().getName());
                }

                if(article.getCurrentStock() < orderedQty ){
                    throw new RuntimeException("Stock insuficiente del insumo: "+ article.getDenomination());
                }

                mad.setQuantity(mad.getQuantity() - orderedQty);

                articleDetailRepository.save(mad);

                // int requiredAmount = mad.getQuantity() * orderedQty;

                // log.info("Articulo base: "+ article.getCurrentStock());
                // log.info("Required amount: "+ requiredAmount);
                // log.info("Manufacturad details quantity:  "+mad.getQuantity());

                // if (article.getCurrentStock() < requiredAmount) {
                //     throw new RuntimeException("Stock insuficiente de artículo base: " + article.getDenomination() + " hay: " + article.getCurrentStock() + "cantidad del ariculo");
                // }
                article.setCurrentStock(article.getCurrentStock() - orderedQty);
                articleRepository.save(article);
            }

            manufacturedArticleRepository.save(mArticle);

            OrderDetails detail = new OrderDetails();
            detail.setOrder(order);
            detail.setManufacturedArticle(mArticle);
            detail.setQuantity(orderedQty);
            detail.setSubTotal(detailDTO.getSubTotal());
            orderDetailsList.add(detail);
        }
        order.setOrderDetails(orderDetailsList);
        orderRepository.save(order);

        Bill bill = new Bill();
        bill.setOrder(order);
        bill.setBillingDate(LocalDate.now());
        bill.setPayment(request.getPayMethod());
        bill.setMpMerchantOrderID(null);
        bill.setMpPreferenceID(null);
        bill.setTotalSale(order.getTotal());

        bill = billRepository.save(bill);

        return OrderServiceImpl.toDto(bill);
    }

    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDTO> responseList = new ArrayList<>();

        for (Order order : orders) {
            List<ProductsOrderedDto> manufacturedArticles = getManufacturedArticleDetails(order);
            Client client = order.getClient();
            ClientDto clientDto = new ClientDto();
            clientDto.setFirstName(client.getName());
            clientDto.setLastName(client.getLastName());
            clientDto.setPhoneNumber(client.getPhoneNumber());
            clientDto.setEmail(client.getEmail());
            clientDto.setBirthDate(client.getBirthDate());
            clientDto.setUsername(client.getUsername());
            clientDto.setPassword(client.getPassword());

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

            responseList.add(dto);
        }

        return responseList;

    }

    private List<ProductsOrderedDto> getManufacturedArticleDetails(Order order) {

        return order.getOrderDetails().stream()
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
                                                d.getArticle().isForSale()
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

        List<ProductsOrderedDto> orderDetails = orders.getOrderDetails().stream().map((detail -> {
                ManufacturedArticle mArticle = detail.getManufacturedArticle();

                return  mapToManufacturedArticleDTO(mArticle,detail.getQuantity());
                })

        ).toList();

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
                .client( OrderServiceImpl.clientToDto(orders.getClient()) )
                .manufacturedArticles(orderDetails)
                .directionToSend(orders.getDirection())
               .build();

    }

    @Override
    public OrderResponseDTO changeOrderStatus(Integer orderId, OrderState orderState) {
       Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Orden no encontrada"));
       order.setOrderState(orderState);
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
               .client( OrderServiceImpl.clientToDto(order.getClient()) )
               .directionToSend(order.getDirection())
               .build();
    }

    @Override
    public List<ProductsOrderedDto> getOrderedProductByUserId(Long userId) {
        List<Order>orderList = orderRepository.findOrderByClient(userId);

        if (orderList.isEmpty()){
            throw new RuntimeException("No hay ordenes asociadas al cliente con id "+userId);
        }
        List<ProductsOrderedDto> dtoList = new ArrayList<>();

        for (Order order : orderList) {
            for (OrderDetails detail : order.getOrderDetails()) {
                ManufacturedArticle article = detail.getManufacturedArticle();

                ProductsOrderedDto dto = new ProductsOrderedDto(
                        article.getIDManufacturedArticle(),
                        article.getName(),
                        article.getDescription(),
                        article.getPrice(),
                        article.getEstimatedTimeMinutes(),
                        article.isAvailable(),
                        detail.getQuantity(),
                        null,
                        null,
                        article.getCategory().getIDCategory()
                );

                dtoList.add(dto);
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
                                                d.getArticle().isForSale()
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
                                            a.isForSale()
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
                            sale.getSalePrice()

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