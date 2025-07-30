package supercell.ElBuenSabor.Models.payload;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import supercell.ElBuenSabor.Models.enums.SaleType;

public record SaleDTO(
    String denomination,
    LocalDate startDate,
    LocalDate endDate,
    LocalTime startTime,
    LocalTime endTime,
    String saleDescription,
    SaleType saleType,
    boolean isActive,
    Double saleDiscount, // 0 to 1, where 1 is 100% of the original price
    InventoryImageDTO inventoryImage,
    List<SaleDetailDTO> saleDetails
) {}
