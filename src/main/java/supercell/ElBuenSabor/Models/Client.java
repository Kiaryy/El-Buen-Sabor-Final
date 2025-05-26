package supercell.ElBuenSabor.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Client extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String auth0Id;

    @OneToOne(cascade = CascadeType.ALL)
    private UserImage userImage;

}

