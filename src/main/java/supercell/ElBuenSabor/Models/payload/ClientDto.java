package supercell.ElBuenSabor.Models.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto{
    private String firstName;
    private String lastName;
    private Integer phoneNumber;
    private String email;
    @JsonFormat (pattern = "MM-yyyy")
    private Date birthDate;
    private String username;
    private String password;
}
