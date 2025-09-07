package supercell.ElBuenSabor.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int quantity;
    private double subTotal;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "manufacturedArticle")
    private ManufacturedArticle manufacturedArticle;
    
    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = true)
    @JsonIgnoreProperties("saleDetails") // prevent infinite recursion
    private Sale sale;
    
}
