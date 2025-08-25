package com.keerthana.bank_app.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public class UserLogin {
    @NotNull(message = "Enter UserId or Account Number!")
    @Column(nullable = false)
    private String loginId;
    @NotNull(message = "Password cannot be empty!")
    @Column(nullable = false)
    private String accPassword;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getAccPassword() {
        return accPassword;
    }

    public void setAccPassword(String accPassword) {
        this.accPassword = accPassword;
    }
}
