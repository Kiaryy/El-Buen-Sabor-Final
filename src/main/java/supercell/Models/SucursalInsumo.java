package supercell.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SucursalInsumo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int sucursalId;

    private long stockActual;

    private long stockMinimo;

    private long stockMaximo;

    @ManyToOne
    @JoinColumn(name = "sucursalId")
    private SucursalEmpresa sucursalEmpresa;

}
