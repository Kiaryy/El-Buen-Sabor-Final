package supercell.ElBuenSabor.controller.auth;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import supercell.ElBuenSabor.Models.Client;
import supercell.ElBuenSabor.repository.ClientRepository;

@RestController
@RequestMapping("/oauth2")
@CrossOrigin(origins = "*")
public class OAuth2Controller {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/success")
    public void getUserInfo(OAuth2AuthenticationToken authentication, HttpServletResponse response) throws IOException {
        Map<String, Object> attributes = authentication.getPrincipal().getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("given_name");
        String lastName = (String) attributes.get("family_name");
        String sub = (String) attributes.get("sub");

        Client client = clientRepository.findByEmail(email).orElseGet(() -> {
            Client newClient = new Client();
            newClient.setEmail(email);
            newClient.setName(name);
            newClient.setLastName(lastName);
            newClient.setAuth0Id(sub);
            return clientRepository.save(newClient);
        });

        // Redirect to frontend URL after saving user
        response.sendRedirect("http://localhost:5173/home");
    }

    @GetMapping("/me")
    public Map<String, Object> whoami(OAuth2AuthenticationToken auth) {
        return auth.getPrincipal().getAttributes();
    }
}
