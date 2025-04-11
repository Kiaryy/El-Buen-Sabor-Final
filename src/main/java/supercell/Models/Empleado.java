package supercell.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import supercell.Models.enums.EmpleadoRole;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long empleadoId;

    private String nombre;
    private String apellido;
    private Long telefono;
    private String correo;
    private EmpleadoRole role;

    @OneToMany(mappedBy = "empleado")
    private List<PedidoVenta> pedidoVentas = new ArrayList<>();

}
