package com.keerthana.bank_app.configuration;

import com.keerthana.bank_app.service.BankUserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http,@Qualifier("userAuthenticationManager") AuthenticationManager authManager, GlobalSecurityExceptionHandler exceptionHandler) throws Exception {
        //AuthenticationManager authManager = new ProviderManager(userAuthProvider());
        http
                .securityMatcher("/user/**","/user/transactions/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/login", "/user/registration").permitAll()
                        .anyRequest().hasRole("USER"))
                .authenticationManager(authManager)
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"message\":\"User logged out successfully\"}");
                        }))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(exceptionHandler)
                        .accessDeniedHandler(exceptionHandler)
                );

        return http.build();
    }
}
