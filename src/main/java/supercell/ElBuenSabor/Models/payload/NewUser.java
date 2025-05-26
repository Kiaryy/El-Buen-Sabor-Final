package supercell.ElBuenSabor.Models.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class NewUser {
    private String firstName;
    private String lastName;
    private Integer phoneNumber;
    private String email;
    private Date birthDate;
}
