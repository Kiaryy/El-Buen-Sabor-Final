package supercell.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

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
}
