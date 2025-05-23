package supercell.ElBuenSabor.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDArticle;

    private String denomination;
    
    private int currentStock;

    private int maxStock;

    private Double buyingPrice;

    @ManyToOne
    @JoinColumn(name = "measuringUnit_id", nullable = false) // MeasuringUnit Foreign Key
    private MeasuringUnit measuringUnit;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false) // Category Foreign Key 
    private Category category;
}
