package supercell.ElBuenSabor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import supercell.ElBuenSabor.Models.payload.BillResponseDTO;
import supercell.ElBuenSabor.Models.payload.OrderRequestDTO;
import supercell.ElBuenSabor.repository.ManufacturedArticleRepository;
import supercell.ElBuenSabor.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<BillResponseDTO> createOrder(@RequestBody OrderRequestDTO request) {
        BillResponseDTO response = orderService.createOrder(request);
        return ResponseEntity.ok(response);
    }
}
