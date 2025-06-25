package supercell.ElBuenSabor.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDSaleDetail;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article; // Nullable

    @ManyToOne
    @JoinColumn(name = "manufactured_article_id")
    private ManufacturedArticle manufacturedArticle; // Nullable

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    @JsonBackReference
    private Sale sale;
}
