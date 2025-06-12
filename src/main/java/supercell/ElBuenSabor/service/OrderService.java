package supercell.ElBuenSabor.service;


import supercell.ElBuenSabor.Models.payload.BillResponseDTO;
import supercell.ElBuenSabor.Models.payload.OrderRequestDTO;

public interface OrderService {
    BillResponseDTO createOrder(OrderRequestDTO orderRequestDTO);
}
