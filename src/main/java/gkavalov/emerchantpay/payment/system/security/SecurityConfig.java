package gkavalov.emerchantpay.payment.system.security;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// TODO enable basic auth
//@Configuration
//@EnableWebSecurity
public class SecurityConfig {

    //@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/transactions/**", "/merchants/*/transactions").authenticated()
                        .anyRequest().permitAll());
        return http.build();
    }

    //@Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("emerchantpay")
                        .password("p4ss")
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}

