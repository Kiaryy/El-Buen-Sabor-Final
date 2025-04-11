package supercell.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SucursalEmpresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDSucursal;

    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String horarioApertura;
    @Column(nullable = false)
    private String horarioCierre;
    
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false) // Foreign key de empresa
    private Empresa empresa;

    @OneToOne
    @JoinColumn(name = "domicilio_id", nullable = false) // Foreign key de domicilio
    private Domicilio domicilio;

    @OneToMany(mappedBy = "sucursalEmpresa", fetch = FetchType.LAZY)
    private List<SucursalInsumo> sucursalInsumos = new ArrayList<>();

    @OneToMany(mappedBy = "sucursal")
    private List<PedidoVenta> pedidoVenta = new ArrayList<>();
}
