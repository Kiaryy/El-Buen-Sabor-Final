package supercell.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Empresa {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long IDEmpresa;
    
    @Id
    private long cuil;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String razonSocial;
}
