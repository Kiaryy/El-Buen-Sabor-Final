package supercell.ElBuenSabor.Models.payload;


public record ProductsOrderedDto(
        Long productId,
        String name,
        String description,
        Double price,
        int estimatedTimeMinutes,
        boolean isAvailable,
        Integer quantityOrdered,
        //List<ManufacturedArticleDetailDTO> manufacturedArticleDetail,
        InventoryImageDTO inventoryImageDTO,
        Long category
) {
}
