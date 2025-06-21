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
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions().disable())
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/article/**").permitAll()
                .requestMatchers("/client/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/oauth2/**", "/login/**", "/").permitAll() // allow login and success handler
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("/oauth2/success", true)
            );

        return http.build();
    }

}
