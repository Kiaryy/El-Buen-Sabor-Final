package supercell.ElBuenSabor.controller;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supercell.ElBuenSabor.Models.enums.OrderState;
import supercell.ElBuenSabor.Models.payload.*;

import supercell.ElBuenSabor.service.OrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Log4j2
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<BillResponseDTO> createOrder(@RequestBody OrderRequestDTO request) {
        BillResponseDTO response = orderService.createOrder(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Integer id) {
        OrderResponseDTO dto = orderService.getOrderById(id);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/user/productOrdered/{userId}")
    public ResponseEntity<List<ProductsOrderedDto>> getOrderedProducts(@PathVariable Long userId) {
        List<ProductsOrderedDto> productsOrderedDto = orderService.getOrderedProductByUserId(userId);
        return ResponseEntity.ok(productsOrderedDto);
    }
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders()
    {
        List<OrderResponseDTO> orderResponseDTOS = orderService.getAllOrders();
        return ResponseEntity.ok(orderResponseDTOS);
    }

    @PatchMapping("/orderState/{orderId}")
    public ResponseEntity<OrderResponseDTO> changeOrderStatus(@PathVariable Integer orderId, @RequestBody OrderState orderState) {
        OrderResponseDTO orderResponseDTO = orderService.changeOrderStatus(orderId,orderState);
        return ResponseEntity.ok(orderResponseDTO);
    }
    @GetMapping("/{orderId}/details-with-promos")
    public ResponseEntity<OrderWithPromosDTO> getOrderWithProductsAndSales(@PathVariable Integer orderId) {
        OrderWithPromosDTO response = orderService.getOrderWithProductsAndPromos(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/statistics")
    public ResponseEntity<OrderStatisticsDTO> getOrderStatistics() {
        OrderStatisticsDTO stats = orderService.getOrderStatistics();
        return ResponseEntity.ok(stats);
    }

    @PostMapping("/createPreference")
    public Preference createPreference(@RequestBody List<UserPreferenceRequest> userItemsPreference ) {
        MercadoPagoConfig.setAccessToken("APP_USR-5808544844698208-051510-6264c6c45bd16af4b067e869f6cb9357-2439047893");

        List<PreferenceItemRequest> items = new ArrayList<>();

        userItemsPreference.forEach(userPreference -> {
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .id("1234") //require
                    .title(userPreference.title()) //require
                    .pictureUrl("http://picture.com/PS5")
                    .categoryId("games")
                    .quantity(userPreference.quantity()) //require
                    .unitPrice(new BigDecimal(userPreference.price())) //require
                    .build();
            items.add(itemRequest);
        });

        // Preference Back url
        PreferenceBackUrlsRequest backUrl = PreferenceBackUrlsRequest.builder()
                .success("http://127.0.0.1:5501/front-end/menu.html")
                .pending("http://127.0.0.1:5501/front-end/menu.html")
                .failure("http://127.0.0.1:5501/front-end/menu.html")
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .backUrls(backUrl)
                .items(items).build();

        PreferenceClient client = new PreferenceClient();
        Preference preference;
        try {
            preference = client.create(preferenceRequest);

        } catch (MPException e) {
            throw new RuntimeException(e);
        } catch (MPApiException e) {
            throw new RuntimeException(e);
        }
        log.info(preference);
        return preference;
    }
}
