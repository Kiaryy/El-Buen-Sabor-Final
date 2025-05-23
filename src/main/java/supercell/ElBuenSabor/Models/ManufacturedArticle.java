package supercell.ElBuenSabor.Models;

import java.util.List;

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

    private String description;

    private Double Price;

    private int estimatedTimeMinutes;

    private boolean isAvailable;

    @OneToMany(mappedBy = "manufacturedArticle", cascade = CascadeType.ALL)
    private List<ManufacturedArticleDetail> manufacturedArticleDetail;
}
