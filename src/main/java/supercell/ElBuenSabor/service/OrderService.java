package supercell.ElBuenSabor.service;


import supercell.ElBuenSabor.Models.payload.BillResponseDTO;
import supercell.ElBuenSabor.Models.payload.OrderRequestDTO;
import supercell.ElBuenSabor.Models.payload.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    BillResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    List<OrderResponseDTO> getAllOrders();
}
