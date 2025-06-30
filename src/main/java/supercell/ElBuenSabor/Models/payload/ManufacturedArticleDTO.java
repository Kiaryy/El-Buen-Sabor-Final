package supercell.ElBuenSabor.Models.payload;

import java.util.List;


public record ManufacturedArticleDTO(
    String name,
    String description,
    int estimatedTimeMinutes,
    boolean isAvailable,
    List<ManufacturedArticleDetailDTO> manufacturedArticleDetail,
    InventoryImageDTO inventoryImageDTO,
    Long category
) {
    
}
