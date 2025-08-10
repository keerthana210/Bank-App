package com.keerthana.bank_app.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public class AdminLogin {

    @NotNull(message = "Enter Admin Id or Admin Email Id")
    @Column(nullable = false)
    private String loginId;

    @NotNull(message = "Password cannot be empty!")
    @Column(nullable = false)
    private String adminPassword;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

}
