package supercell.ElBuenSabor.Models.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Getter
@Setter
public class OrderRequestDTO {
    private LocalTime estimatedFinishTime;
    private double total;
    private double totalCost;
    private int orderStateId;
    private int orderTypeId;
    private long payMethodId;
    private LocalDate orderDate;
    private boolean takeAway;
    private long clientId;
    private int subsidiaryId;
    private List<OrderDetailDTO> orderDetails;

    @Getter @Setter
    public static class OrderDetailDTO {
        private long manufacturedArticleId;
        private int quantity;
        private double subTotal;
    }
}

