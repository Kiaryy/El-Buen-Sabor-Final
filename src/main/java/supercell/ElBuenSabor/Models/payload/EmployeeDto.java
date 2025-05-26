package supercell.ElBuenSabor.Models.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import supercell.ElBuenSabor.Models.enums.Role;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto{
    private String firstName;
    private String lastName;
    private Integer phoneNumber;
    private String email;
    private Date birthDate;
    private String username;
    private String password;
    private Role role;
    private Double salary;
    private LocalDate shift;
}
