package supercell.Models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @Column(precision = 10, scale = 2)
    private Double shippingCost;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false) // Category Foreign Key 
    private Category category;

    @OneToMany(mappedBy = "provider", fetch = FetchType.LAZY)
    private List<Article> articles;

}
