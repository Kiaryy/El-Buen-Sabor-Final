package supercell.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoVentaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pedidoDetalleId;

    private int cantidad;

    private Double subTotal;

    @OneToMany(fetch = FetchType.EAGER)
    private List<PedidoVenta> pedidoVentas = new ArrayList<>();

}
