package supercell.ElBuenSabor.Models.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderWithPromosDTO {
    private Integer orderId;
    private LocalDate orderDate;
    private String clientName;
    private List<ProductsOrderedDto> orderedProducts;
    private List<SaleResponseDTO> promotions;
}

