package supercell.ElBuenSabor.Models.payload;

import java.util.List;

public record ProviderDTO(
    Double shippingCost,
    Long category,
    List<Long> articles
) {
}