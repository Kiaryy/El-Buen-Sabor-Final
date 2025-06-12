package supercell.ElBuenSabor.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate billingDate;
    private Long mpPaymentID;
    private Integer mpMerchantOrderID;
    private String mpPreferenceID;
    private double totalSale;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Getters and setters
}
