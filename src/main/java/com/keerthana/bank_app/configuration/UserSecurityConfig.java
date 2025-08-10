package com.keerthana.bank_app.configuration;

import com.keerthana.bank_app.service.BankUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@Order(1)
public class UserSecurityConfig {

    private BankUserService bankUserService;
    private PasswordEncoder passwordEncoder;
    public UserSecurityConfig(BankUserService bankUserService,PasswordEncoder passwordEncoder) {
        this.bankUserService = bankUserService;
        this.passwordEncoder = passwordEncoder;
    }
    @Bean(name = "userAuthenticationProvider")
    public DaoAuthenticationProvider userAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(bankUserService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authManager = new ProviderManager(userAuthProvider());
        http
                .securityMatcher("/user/**","/transactions/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/login", "/user/registration").permitAll()
                        .anyRequest().hasAuthority("USER"))
                .authenticationManager(authManager)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
