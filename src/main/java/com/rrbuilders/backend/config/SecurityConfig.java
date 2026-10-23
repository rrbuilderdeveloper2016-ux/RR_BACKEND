package com.rrbuilders.backend.config;

import com.rrbuilders.backend.security.JwtAuthEntryPoint;
import com.rrbuilders.backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthEntryPoint unauthorizedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints: Property exploration, Projects, Locations
                        .requestMatchers(HttpMethod.GET, "/api/properties", "/api/properties/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/projects", "/api/projects/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/locations", "/api/locations/**").permitAll()

                        // Public inquiry submission (anyone can submit an inquiry/requirement)
                        .requestMatchers(HttpMethod.POST, "/api/inquiries", "/api/inquiries/**").permitAll()

                        // Public auth login
                        .requestMatchers("/api/auth/login").permitAll()

                        // Static / error endpoints
                        .requestMatchers("/error").permitAll()

                        // Protected Admin endpoints: reading enquiries, updating statuses, deleting, lead stats
                        .requestMatchers(HttpMethod.GET, "/api/inquiries/**").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/api/inquiries/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/inquiries/**").authenticated()
                        .requestMatchers("/api/auth/me", "/api/auth/change-password").authenticated()

                        // Any other request requires authentication
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
