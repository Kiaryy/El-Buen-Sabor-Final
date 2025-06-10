package supercell.ElBuenSabor.Models.payload;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BillResponseDTO {
    private Long id;
    private LocalDate billingDate;
    private Integer mpPaymentID;
    private Integer mpMerchantOrderID;
    private String mpPreferenceID;
    private double totalSale;
    private Long orderId;
}
