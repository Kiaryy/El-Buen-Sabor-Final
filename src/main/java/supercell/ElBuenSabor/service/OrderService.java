package supercell.ElBuenSabor.service;

import supercell.ElBuenSabor.Models.enums.OrderState;
import supercell.ElBuenSabor.Models.payload.*;

import java.util.List;

public interface OrderService {
    BillResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    List<OrderResponseDTO> getAllOrders();

    OrderResponseDTO getOrderById(Integer id);

    OrderResponseDTO changeOrderStatus(Integer orderId, OrderState orderState);

    List<ProductsOrderedDto> getOrderedProductByUserId(Long userId);

    OrderWithPromosDTO getOrderWithProductsAndPromos(Integer orderId);

    OrderStatisticsDTO getOrderStatistics();
}
