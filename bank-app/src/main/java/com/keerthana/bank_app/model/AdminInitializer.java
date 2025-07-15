package com.keerthana.bank_app.model;

import com.keerthana.bank_app.configuration.SecurityConfig;
import com.keerthana.bank_app.enums.AdminAccessLevel;
import com.keerthana.bank_app.enums.Role;
import com.keerthana.bank_app.repository.AdminRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private SecurityConfig securityConfig;

    @PostConstruct
    public void initDefaultAdmin() {
        String defaultEmail = "admin@hirabank.com";

        if (!adminRepository.existsByEmailId(defaultEmail)) {
            Admin admin = new Admin();
            admin.setAdminName("Root");
            admin.setContactNumber("9000000003");
            admin.setAccess(AdminAccessLevel.FULL_ACCESS);
            admin.setEmailId(defaultEmail);
            admin.setAdminPassword(securityConfig.passwordEncoder().encode("Admin@123"));
            admin.setRole(Role.ROLE_ADMIN);

            adminRepository.save(admin);
        }
    }
}

