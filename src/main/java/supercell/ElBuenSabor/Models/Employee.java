package supercell.ElBuenSabor.Models;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import supercell.ElBuenSabor.Models.enums.Role;
import supercell.ElBuenSabor.Models.enums.Shift;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role employeeRole;

    private Double salary;

    @Enumerated(EnumType.STRING)
    private Shift shift;

    private String username;
    private String password;
    private boolean isEnabled;

        
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Domicile> domiciles;
}

