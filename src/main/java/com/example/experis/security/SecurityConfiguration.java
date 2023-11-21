package com.example.experis.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.*;

@Configuration
public class SecurityConfiguration {

    @Bean
    public DatabaseUserDetailsService userDetailsService(){
        return new DatabaseUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // allow for all api requests
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/ingredient", "/special-offer/**", "/users")
                        .hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/pizza/**")
                        .hasAuthority("ADMIN")
                        .requestMatchers("/pizza", "/pizza/**")
                        .hasAnyAuthority("ADMIN", "USER")
                        .anyRequest().permitAll()

                )
                .csrf().disable()
                .formLogin(withDefaults())
                .logout(withDefaults());

        return http.build();
    }

}