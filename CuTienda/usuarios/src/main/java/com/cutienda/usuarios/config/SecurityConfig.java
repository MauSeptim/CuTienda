package com.cutienda.usuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/cutienda/login").permitAll() // Permitir acceso sin autenticación
                        .requestMatchers("/cutienda/registro").permitAll() // Permitir acceso a registro sin autenticación
                        .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
                )
                .csrf(csrf -> csrf.disable()) // Desactivar CSRF
                .formLogin(formLogin -> formLogin
                        .loginPage("/cutienda/login") // Configura la página de inicio de sesión
                        .permitAll() // Permite el acceso a la página de inicio de sesión sin autenticación
                );
        return http.build();
    }
}
