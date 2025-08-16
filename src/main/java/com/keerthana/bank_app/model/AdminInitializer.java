package com.keerthana.bank_app.model;

import com.keerthana.bank_app.enums.AdminAccessLevel;
import com.keerthana.bank_app.enums.Role;
import com.keerthana.bank_app.service.AdminService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {
    private AdminService adminService;
    @Autowired
    public AdminInitializer(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostConstruct
    public void initDefaultAdmin() {
        String defaultEmail = "admin@hirabank.com";

        if (!adminService.existsByEmailId(defaultEmail)) {
            Admin admin = new Admin();
            admin.setAdminName("Root");
            admin.setContactNumber("9000000003");
            admin.setAccess(AdminAccessLevel.FULL_ACCESS);
            admin.setEmailId(defaultEmail);
            admin.setAdminPassword("Admin@123");
            admin.setRole(Role.ADMIN);
            Admin savedAdmin = adminService.saveAdmin(admin);

            String customId = "HIRA_AD" + String.format("%03d", savedAdmin.getId());
            savedAdmin.setAdminId(customId);

            adminService.saveAdmin(savedAdmin);
        }
    }
}

