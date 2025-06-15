package supercell.ElBuenSabor.Models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import supercell.ElBuenSabor.Models.enums.SaleType;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDSale;
    
    private String denomination;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String saleDescription;
    private Double salePrice;

    @Enumerated(EnumType.STRING)
    private SaleType saleType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_image_id", referencedColumnName = "IDInventoryImage")
    private InventoryImage inventoryImage;

    @OneToMany
    @JsonManagedReference
    private List<ManufacturedArticle> manufacturedArticle;

}
