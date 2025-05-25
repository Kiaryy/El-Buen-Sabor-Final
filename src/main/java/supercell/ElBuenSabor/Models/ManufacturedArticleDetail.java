package supercell.ElBuenSabor.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
public class ManufacturedArticleDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDManufacturedArticleDetail;

    private int quantity;
    
    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false) // Article Foreign Key
    private Article article;

    @ManyToOne
    @JoinColumn(name = "manufactured_article_id")
    @JsonBackReference
    private ManufacturedArticle manufacturedArticle;
}
