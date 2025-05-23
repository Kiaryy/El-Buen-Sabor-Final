package supercell.ElBuenSabor.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long IDCompany;
        
    @Id
    private long cuil;
    private String Name;
    private String SocialReason;
}
