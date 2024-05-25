package ltd.matrixstudios.spaces.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Class created on 5/23/2024
 *
 * @author Max C.
 * @project spaces
 * @website https://solo.to/redis
 */
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class WebSecurityController {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/**").permitAll()
        ).csrf(csrf -> csrf
                .ignoringRequestMatchers("/**"));

        return http.build();
    }

}