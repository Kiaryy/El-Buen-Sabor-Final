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
public class Domicile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDDomicile;

    private String street;
    private String zipCode;

    private int number;
    
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false) // Location Foreign Key
    private Location location;   
    
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = true)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = true)
    private Employee employee;
}
