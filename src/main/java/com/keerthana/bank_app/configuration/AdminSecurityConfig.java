package com.keerthana.bank_app.configuration;

import com.keerthana.bank_app.service.BankAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(2)
public class AdminSecurityConfig {

    private BankAdminService bankAdminService;
    private PasswordEncoder passwordEncoder;


    public AdminSecurityConfig(BankAdminService bankAdminService,PasswordEncoder passwordEncoder) {
        this.bankAdminService = bankAdminService;
        this.passwordEncoder = passwordEncoder;

    }
    @Bean(name="adminAuthenticationProvider")
    public DaoAuthenticationProvider adminAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(bankAdminService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authManager = new ProviderManager(adminAuthProvider());
        http
                .securityMatcher("/admin/**","/transactions/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/login").permitAll()
                        .requestMatchers("/admin/new-admin-creation").hasAuthority("FULL_ACCESS")
                        .anyRequest().hasRole("ADMIN"))
                .authenticationManager(authManager)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
