package supercell.ElBuenSabor.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    private int estimatedTimeMinutes;

    private boolean isAvailable;

    @OneToMany(mappedBy = "manufacturedArticle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ManufacturedArticleDetail> manufacturedArticleDetail;


}
