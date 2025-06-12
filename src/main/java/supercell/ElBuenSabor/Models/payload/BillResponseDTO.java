package supercell.ElBuenSabor.Models.payload;


import lombok.Data;
import supercell.ElBuenSabor.Models.enums.PayMethod;

import java.time.LocalDate;

@Data
public class BillResponseDTO {
    private Long id;
    private LocalDate billingDate;
    private PayMethod paymentMethod;
    private Integer mpMerchantOrderID;
    private String mpPreferenceID;
    private double totalSale;
    private Long orderId;
}
