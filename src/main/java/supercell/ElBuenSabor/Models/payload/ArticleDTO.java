package supercell.ElBuenSabor.Models.payload;

public record ArticleDTO(
    String denomination,
    int currentStock,
    int maxStock,
    Double buyingPrice,
    Long measuringUnit,
    Long category

) {
    
}
