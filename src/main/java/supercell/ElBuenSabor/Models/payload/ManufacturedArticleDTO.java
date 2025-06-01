package supercell.ElBuenSabor.Models.payload;

import java.util.List;


public record ManufacturedArticleDTO(
    String name,
    String description,
    Double price,
    int estimatedTimeMinutes,
    boolean isAvailable,
    List<ManufacturedArticleDetailDTO> manufacturedArticleDetail,
    InventoryImageDTO inventoryImageDTO
) {
    
}
