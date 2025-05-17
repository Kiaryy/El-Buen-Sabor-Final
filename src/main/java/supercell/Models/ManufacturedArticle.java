package supercell.Models;

import java.util.ArrayList;
import java.util.List;

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
    private String recipe;

    private int estimatedTimeMinutes;


    @OneToMany(mappedBy = "manufacturedArticle", fetch = FetchType.LAZY)
    private List<Article> articles = new ArrayList<>();
}
