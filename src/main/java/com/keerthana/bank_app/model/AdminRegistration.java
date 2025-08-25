package com.keerthana.bank_app.model;

import com.keerthana.bank_app.enums.AdminAccessLevel;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AdminRegistration {
    @NotNull(message = "Name cannot be empty!")
    private String adminName;
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    @NotNull(message = "Contact Number cannot be empty!")
    private String contactNumber;
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@hirabank\\.com$", message = "Invalid email address")
    @NotNull(message = "Email cannot be empty!")
    @Column(unique = true)
    private String emailId;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Access Level cannot be empty!")
    private AdminAccessLevel access;
    private String adminPassword;

    public AdminRegistration() {
        this.adminPassword = "Admin@123";
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        if (adminPassword == null || adminPassword.isBlank()) {
            this.adminPassword = "Admin@123";
        } else {
            this.adminPassword = adminPassword;
        }
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public AdminAccessLevel getAccess() {
        return access;
    }

    public void setAccess(AdminAccessLevel access) {
        this.access = access;
    }
}
