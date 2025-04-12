package supercell.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import supercell.Models.enums.Estado;
import supercell.Models.enums.FormaPago;
import supercell.Models.enums.TipoEnvio;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pedidoVentaId;

    @Column(precision = 10, scale = 2)
    private Double subtotal;

    @Column(precision = 10, scale = 2)
    private Double descuento;

    @Column(name = "gastos_envio", precision = 10, scale = 2)
    private Double gastosEnvio;

    @Column(precision = 10, scale = 2)
    private Double total;

    @Column(name = "total_costo", precision = 10, scale = 2)
    private Double totalCosto;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_envio")
    private TipoEnvio tipoEnvio;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pago")
    private FormaPago formaPago;

    @Column(name = "fecha_pedido")
    private LocalDate fechaPedido;

    @ManyToOne
    @JoinColumn(name = "sucursalId")
    private SucursalEmpresa sucursal;

    @ManyToOne
    @JoinColumn(name = "empleadoId")
    private Empleado empleado;


}
