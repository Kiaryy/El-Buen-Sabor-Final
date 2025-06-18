package supercell.ElBuenSabor.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import supercell.ElBuenSabor.Models.*;
import supercell.ElBuenSabor.Models.enums.OrderType;
import supercell.ElBuenSabor.Models.payload.*;
import supercell.ElBuenSabor.repository.*;
import supercell.ElBuenSabor.service.OrderService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public BillResponseDTO createOrder(OrderRequestDTO request) {

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        /*PayMethod payMethod = payMethodRepository.findById(request.getPayMethodId())
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
        */
        if(client == null) {
            throw new RuntimeException("Cliente o metodo de pago no encontrado");
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
        List<OrderDetails> orderDetailsList = new ArrayList<>();

        for (OrderRequestDTO.OrderDetailDTO detailDTO : request.getOrderDetails()) {
            ManufacturedArticle mArticle = manufacturedArticleRepository.findById(detailDTO.getManufacturedArticleId())
                    .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));

            int orderedQty = detailDTO.getQuantity();

            // if (mArticle.getStock() < orderedQty) {
            //     throw new RuntimeException("Stock insuficiente de: " + mArticle.getName());
            // }
            // mArticle.setStock(mArticle.getStock() - orderedQty);

            for (ManufacturedArticleDetail mad : mArticle.getManufacturedArticleDetail()) {
                Article article = mad.getArticle();
                // int requiredAmount = mad.getQuantity() * orderedQty;

                // log.info("Articulo base: "+ article.getCurrentStock());
                // log.info("Required amount: "+ requiredAmount);
                // log.info("Manufacturad details quantity:  "+mad.getQuantity());

                // if (article.getCurrentStock() < requiredAmount) {
                //     throw new RuntimeException("Stock insuficiente de artículo base: " + article.getDenomination() + " hay: " + article.getCurrentStock() + "cantidad del ariculo");
                // }
                // article.setCurrentStock(article.getCurrentStock() - requiredAmount);
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
        ClientDto clientDto = new ClientDto();

        return orders.stream().map(order -> OrderResponseDTO.builder()
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
                .build()).collect(Collectors.toList());
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
        dto.setUsername(client.getUsername());
        dto.setFirstName(client.getName());
        dto.setLastName(client.getLastName());
        dto.setPhoneNumber(client.getPhoneNumber());
        dto.setBirthDate(client.getBirthDate());
        dto.setEmail(client.getEmail());
        dto.setDomiciles(domicileDTOS);
        return dto;
    }
}
