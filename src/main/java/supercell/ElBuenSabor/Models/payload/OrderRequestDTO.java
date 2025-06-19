package supercell.ElBuenSabor.Models.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import supercell.ElBuenSabor.Models.enums.OrderState;
import supercell.ElBuenSabor.Models.enums.OrderType;
import supercell.ElBuenSabor.Models.enums.PayMethod;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {
    private LocalTime estimatedFinishTime;
    private double total;
    private double totalCost;
    private OrderState orderState;
    private OrderType orderType;
    private PayMethod payMethod;
    private LocalDate orderDate;
    private boolean isTakeAway;
    private long clientId;
    private int subsidiaryId;
    private String direction;
    private List<OrderDetailDTO> orderDetails;

    @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
    public static class OrderDetailDTO {
        private long manufacturedArticleId;
        private int quantity;
        private double subTotal;
    }
}
