package supercell.ElBuenSabor.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class WebConfig  {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .headers(headers -> headers.frameOptions().disable())
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/article/**").permitAll()
                                .requestMatchers("/client/**").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/api/orders/**").permitAll()
                        .anyRequest().permitAll()
                        // Protected paths
                        /*
                        * .requestMatchers("/insumos/**").hasAuthority("ADMIN")
                        * */
                )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
