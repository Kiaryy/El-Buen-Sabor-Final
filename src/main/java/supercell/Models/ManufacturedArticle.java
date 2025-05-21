package supercell.Models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @Column(precision = 10, scale = 2)
    private Double Price;

    private int estimatedTimeMinutes;

    private boolean isAvailable;

    @OneToMany(mappedBy = "manufacturedArticle", fetch = FetchType.LAZY)
    private List<ManufacturedArticleDetail> manufacturedArticleDetail;
}
