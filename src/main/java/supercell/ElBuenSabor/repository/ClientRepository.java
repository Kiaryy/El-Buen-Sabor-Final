package supercell.ElBuenSabor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import supercell.ElBuenSabor.Models.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmailAndPassword(String username, String password);
    Optional<Client> findByEmail(String email);
}

