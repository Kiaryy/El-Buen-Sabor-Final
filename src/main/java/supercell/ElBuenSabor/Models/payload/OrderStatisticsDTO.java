package supercell.ElBuenSabor.Models.payload;

import lombok.Data;

import java.util.Map;

@Data
public class OrderStatisticsDTO {
    private long totalOrders;
    private double totalRevenue;
    private double totalCost;
    private double profit;
    private long canceledOrders;
    private long pendingOrders;
    private long deliveredOrders;
    private Map<String, Long> ordersByType;
}

