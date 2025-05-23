package supercell.ElBuenSabor.Models;

import java.time.LocalDate;

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
public class HistoryPurchased {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDHistoryPurchased;

    private LocalDate purchaseDate;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false) // Provider foreign key
    private Provider provider;
}
