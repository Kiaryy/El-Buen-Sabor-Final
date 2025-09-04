package supercell.ElBuenSabor.Models.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private List<ProductsOrderedDto> manufacturedArticles;
    private List<ArticleDTO> orderedArticles;
    private List<SaleResponseDTO> sales;
}

