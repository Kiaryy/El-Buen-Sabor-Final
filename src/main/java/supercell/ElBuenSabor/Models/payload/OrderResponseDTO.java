package supercell.ElBuenSabor.Models.payload;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class OrderResponseDTO {
    private Integer id;
    private LocalTime estimatedFinishTime;
    private double total;
    private double totalCost;
    private LocalDate orderDate;
    private String orderState;
    private String orderType;
    private String payMethod;
    private ClientDto client;
    private String directionToSend;
    private Integer subsidiaryId;
}

