package supercell.ElBuenSabor.Models.payload;


import java.util.List;

public record ProductsOrderedDto(
        Long productId,
        String name,
        String description,
        Double price,
        int estimatedTimeMinutes,
        boolean isAvailable,
        Integer quantityOrdered,
       // List<ManufacturedArticleDetailDTO> manufacturedArticleDetail,
        List<ArticleDTO> articles,
        InventoryImageDTO inventoryImageDTO,
        Long category
) {
}
