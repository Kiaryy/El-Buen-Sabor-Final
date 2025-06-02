package supercell.ElBuenSabor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers("/auth/**").permitAll()
                        // Protected paths
                        /*
                        * .requestMatchers("/insumos/**").hasAuthority("ADMIN")
                        * */
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
