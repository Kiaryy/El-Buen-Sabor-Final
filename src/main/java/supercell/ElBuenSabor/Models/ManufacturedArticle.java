package supercell.ElBuenSabor.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManufacturedArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDManufacturedArticle;

    private String name;
    
    private String description;

    private Double price;
    private Integer stock;
    private int estimatedTimeMinutes;

    private boolean isAvailable;

    @OneToMany(mappedBy = "manufacturedArticle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ManufacturedArticleDetail> manufacturedArticleDetail;

    @ManyToOne
    @JoinColumn(name = "category_id") // Puedes personalizar el nombre de la columna
    private Category category;  

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_image_id", referencedColumnName = "IDInventoryImage")
    private InventoryImage manufacInventoryImage;
}
