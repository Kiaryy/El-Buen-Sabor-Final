package supercell.ElBuenSabor.Models.payload;

public record DomicileDTO(
    String street,
    String zipcode,
    int number,
    Long location
) {
    
}
