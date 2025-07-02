package supercell.ElBuenSabor.Models.payload;

import java.time.LocalDate;
import java.util.List;

public record SaleResponseDTO(
        Integer id,
        String denomination,
        LocalDate startDate,
        LocalDate endDate,
        String saleDescription,
        List<ArticleDTO> articles,
        double salePrice
) {}

