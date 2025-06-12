package supercell.ElBuenSabor.Models.payload;

public record UserPreferenceRequest(
        String title,
        int quantity,
        String price
){}
