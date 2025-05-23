package supercell.ElBuenSabor.Models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDProvider;
    
    private LocalDate lastShipmentDate;

    private Double shippingCost;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false) // Category Foreign Key 
    private Category category;

    @ManyToMany
    @JoinTable(
        name = "provider_articles",
        joinColumns = @JoinColumn(name = "provider_id"),
        inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private List<Article> articles;

}
