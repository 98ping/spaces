package ltd.matrixstudios.spaces.security;

import ltd.matrixstudios.spaces.login.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public LoginSuccessHandler successHandler;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**").permitAll()
                        .anyRequest().authenticated()
                ).formLogin((form) -> form
                        .successHandler(successHandler)
                        .failureUrl("/login?error=true")
                        .loginPage("/login")
                        .permitAll()
                );

        return http.build();
    }

}