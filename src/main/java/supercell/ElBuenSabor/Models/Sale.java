package supercell.ElBuenSabor.Models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
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
    private Double saleDiscount;
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    private SaleType saleType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_image_id", referencedColumnName = "IDInventoryImage")
    private InventoryImage inventoryImage;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("sale") 
    private List<SaleDetail> saleDetails;
}
