package supercell.ElBuenSabor.Models.payload;

public record SaleDetailDTO(
    Long id,
    int quantity,
    String type // "ARTICLE" or "MANUFACTURED"
) {}
