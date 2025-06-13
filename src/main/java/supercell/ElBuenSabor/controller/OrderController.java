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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import supercell.ElBuenSabor.Models.payload.BillResponseDTO;
import supercell.ElBuenSabor.Models.payload.OrderRequestDTO;

import supercell.ElBuenSabor.Models.payload.UserPreferenceRequest;
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
