package com.keerthana.bank_app.configuration;

import com.keerthana.bank_app.service.BankAdminService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(2)
public class AdminSecurityConfig {
    private BankAdminService bankAdminService;
    private PasswordEncoder passwordEncoder;

    public AdminSecurityConfig(BankAdminService bankAdminService, PasswordEncoder passwordEncoder) {
        this.bankAdminService = bankAdminService;
        this.passwordEncoder = passwordEncoder;

    }

    @Bean(name = "adminAuthenticationProvider")
    public DaoAuthenticationProvider adminAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(bankAdminService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http, @Qualifier("adminAuthenticationManager") AuthenticationManager authManager, GlobalSecurityExceptionHandler exceptionHandler) throws Exception {
        http
                .headers(headers -> headers
                        .cacheControl(cache -> {})
                        .httpStrictTransportSecurity(hsts -> hsts.includeSubDomains(true).preload(true))
                )
                .securityMatcher("/admin/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/login").permitAll()
                        .requestMatchers("/admin/new-admin-creation").hasAuthority("FULL_ACCESS")
                        .anyRequest().hasRole("ADMIN"))
                .authenticationManager(authManager)
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"message\":\"Admin logged out successfully\"}");
                        }))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .and()
                        .sessionFixation(sessionFixation -> sessionFixation.migrateSession())
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(exceptionHandler)
                        .accessDeniedHandler(exceptionHandler)
                );
        return http.build();
    }
}
