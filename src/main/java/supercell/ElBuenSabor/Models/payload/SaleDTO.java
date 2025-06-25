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
    Double salePrice,
    SaleType saleType,
    boolean isActive,
    InventoryImageDTO inventoryImage,
    List<SaleDetailDTO> saleDetails
) {}
