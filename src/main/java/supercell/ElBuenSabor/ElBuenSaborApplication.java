package supercell.ElBuenSabor;

import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElBuenSaborApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElBuenSaborApplication.class, args);
				System.out.println("BACKEND INITIALIZED AT: " + LocalDate.now());
	}

}
