package com.keerthana.bank_app.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("adminAuthenticationManager")
    public AuthenticationManager adminAuthenticationManager(
            @Qualifier("adminAuthenticationProvider") AuthenticationProvider provider) {
        return new ProviderManager(provider);
    }

    @Primary
    @Bean("userAuthenticationManager")
    public AuthenticationManager userAuthenticationManager(
            @Qualifier("userAuthenticationProvider") AuthenticationProvider provider) {
        return new ProviderManager(provider);
    }
}
