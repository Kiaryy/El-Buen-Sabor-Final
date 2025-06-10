package supercell.ElBuenSabor.Models;

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
public class PayMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPayMethod;
    private String payMethod;
}
