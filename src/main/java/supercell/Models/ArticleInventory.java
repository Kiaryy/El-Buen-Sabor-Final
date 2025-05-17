package supercell.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDArticle;
    
    @Column(precision = 10, scale = 2)
    private Double buyingprice;

    private int currentStock;
    private int maxStock;

    private boolean isForElaboration;
}
