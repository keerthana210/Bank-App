package com.keerthana.bank_app.model;

import jakarta.validation.constraints.NotNull;

public class AdminLogin {

    @NotNull(message = "Admin ID cannot be empty!")
    private Long adminId;

    @NotNull(message = "Password cannot be empty!")
    private String adminPassword;

    public Long getAdminId() {
        return adminId;
    }

    public void setId(Long adminId) {
        this.adminId = adminId;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

}
