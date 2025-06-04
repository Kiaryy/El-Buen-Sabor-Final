package supercell.ElBuenSabor.Models.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto{
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Date birthDate;
    private List<DomicileDTO> domiciles;
    private String username;
    private String password;
}
