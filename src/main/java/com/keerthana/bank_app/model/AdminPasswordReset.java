package com.keerthana.bank_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;

public class AdminPasswordReset {
    @NotNull(message = "Id cannot be empty!")
    @JsonIgnore
    private String adminId;

    @NotNull(message = "Old password cannot be empty!")
    private String oldAdminPassword;
    @NotNull(message = "New Password cannot be empty!")
    private String newAdminPassword;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getOldAdminPassword() {
        return oldAdminPassword;
    }

    public void setOldAdminPassword(String oldAdminPassword) {
        this.oldAdminPassword = oldAdminPassword;
    }

    public String getNewAdminPassword() {
        return newAdminPassword;
    }

    public void setNewAdminPassword(String newAdminPassword) {
        this.newAdminPassword = newAdminPassword;
    }
}
