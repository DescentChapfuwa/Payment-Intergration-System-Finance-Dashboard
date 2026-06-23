package com.techsensei.payment_intergration_system.backend.security.config;

import com.techsensei.payment_intergration_system.backend.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .cors(Customizer.withDefaults())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/auth/**")
                                                .permitAll()
                                                .requestMatchers("/webhooks/**")
                                                .permitAll()
                                                .requestMatchers("/payment/**")
                                                .permitAll()
                                                .requestMatchers("/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**")
                                                .permitAll()
                                                .anyRequest()
                                                .authenticated())
                                .exceptionHandling(
                                                exception -> exception.accessDeniedHandler((request, response, ex) -> {
                                                        response.setStatus(HttpStatus.FORBIDDEN.value());
                                                        response.setContentType("application/json");
                                                        response.setCharacterEncoding("UTF-8");

                                                        String json = """
                                                                        {
                                                                        "status":403,
                                                                        "message":"Access Denied"
                                                                        }
                                                                        """;

                                                        response.getWriter().write(json);
                                                        response.getWriter().flush();
                                                }))
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
