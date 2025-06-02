package supercell.ElBuenSabor.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class Person {
    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Date birthDate;

}