package supercell.ElBuenSabor.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import supercell.ElBuenSabor.Models.*;
import supercell.ElBuenSabor.Models.enums.OrderType;
import supercell.ElBuenSabor.Models.payload.*;
import supercell.ElBuenSabor.repository.*;
import supercell.ElBuenSabor.service.OrderService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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

        Order order = new Order();
        order.setEstimatedFinishTime(request.getEstimatedFinishTime());
        order.setTotal(request.getTotal());
        order.setTotalCost(request.getTotalCost());
        order.setOrderDate(request.getOrderDate());
        order.setClient(client);
        order.setOrderState(request.getOrderState());
        order.setOrderType(request.isTakeAway()? OrderType.TAKEAWAY : OrderType.DELIVERY );
        order.setPayMethod(request.getPayMethod());
        order.setSubsidiaryId(request.getSubsidiaryId());

    /*
        List<OrderDetails> orderDetailsList = new ArrayList<>();

        for (OrderRequestDTO.OrderDetailDTO detailDTO : request.getOrderDetails()) {
            ManufacturedArticle mArticle = manufacturedArticleRepository.findById(detailDTO.getManufacturedArticleId())
                    .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));

            int orderedQuantity  = detailDTO.getQuantity();
            if (mArticle.getStock() < orderedQuantity) {
                throw new RuntimeException("Stock insuficiente para: " + mArticle.getName());
            }

            mArticle.setStock(mArticle.getStock() - orderedQuantity);
            manufacturedArticleRepository.save(mArticle);
            *//*
                    for (ManufacturedArticleDetail mad : mArticle.getManufacturedArticleDetail()) {
                        Article baseArticle = mad.getArticle();
                        int required = mad.getQuantity() * orderedQuantity;
                        if (baseArticle.getCurrentStock() < required) {
                            throw new RuntimeException("Stock insuficiente de artículo base: " + baseArticle.getName());
                        }
                        baseArticle.setCurrentStock(baseArticle.getCurrentStock() - required);
                        mad.setArticle(baseArticle);
                        manufacturedArticleRepository.save();
                    }
                    *//*
            OrderDetails detail = new OrderDetails();
            detail.setOrder(order);
            detail.setManufacturedArticle(mArticle);
            detail.setQuantity(detailDTO.getQuantity());
            detail.setSubTotal(detailDTO.getSubTotal());

            orderDetailsList.add(detail);
        }
        order.setOrderDetails(orderDetailsList);
    */
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
