package ru.lastenko.library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/", HttpMethod.GET.name())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/book", HttpMethod.GET.name())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/book/**", HttpMethod.GET.name())).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/book/**", HttpMethod.POST.name())).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/actuator/**")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/datarest/**")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        .anyRequest().denyAll())
                .formLogin(withDefaults())
                // Настройки для H2 консоли
                .headers(headers -> headers
                        // Отключаем X-Frame-Options или устанавливаем его в SAMEORIGIN
                        .frameOptions().sameOrigin()
                        // Устанавливаем Content Security Policy
                        .contentSecurityPolicy("frame-ancestors 'self';")
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
}
