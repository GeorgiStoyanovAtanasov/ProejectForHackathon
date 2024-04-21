package com.example.demo.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/event/register").permitAll()
                        .requestMatchers("/home").permitAll()//hasAnyAuthority("USER", "ADMIN", "ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/chat/**").hasAnyAuthority("USER", "ADMIN", "ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/chat/send").hasAnyAuthority("USER", "ADMIN", "ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/event/user/list").hasAnyAuthority("USER", "ADMIN", "ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/view").hasAnyAuthority("USER", "ADMIN", "ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/logout").hasAnyAuthority("USER", "ADMIN", "ROLE_USER", "ROLE_ADMIN")
                        .anyRequest().hasAuthority("ADMIN")
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/home")
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .permitAll()
                );

        return http.build();
    }
}


