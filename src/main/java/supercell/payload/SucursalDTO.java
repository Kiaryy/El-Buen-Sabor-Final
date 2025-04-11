package supercell.payload;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SucursalDTO {
    private Long IDSucursal;
    private String nombre;
    private String horarioApertura;
    private String horarioCierre;
}
