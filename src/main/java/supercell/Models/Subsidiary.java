package supercell.Models;

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
public class Subsidiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDSubsidiary;

    private String name;
    private String openingHour;
    private String closingHour;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false) // Company Foreign key
    private Company company;

    @OneToOne
    @JoinColumn(name = "domicile_id", nullable = false) // Domicile Foreign Key
    private Domicile domicile;


    // @OneToMany(mappedBy = "sucursalEmpresa", fetch = FetchType.LAZY)
    // private List<SucursalInsumo> sucursalInsumos = new ArrayList<>();

    // @OneToMany(mappedBy = "sucursal")
    // private List<PedidoVenta> pedidoVenta = new ArrayList<>();
}
