package com.keerthana.bank_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UserRegistration {
    @Pattern(regexp = "^[0-9]{8}$", message = "Account number must be exactly 8 digits")
    @NotNull(message = "Account number cannot be empty!")
    private String accNumber;
    @NotNull(message = "Account holder name cannot be null")
    private String accHolderName;
    @NotNull(message = "Account holder location cannot be empty!")
    private String accHolderLocation;
    @NotNull(message = "Account password cannot be empty!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$", message = "Password must contain at least 8 characters, including atleast 1 uppercase, atleast 1 lowercase, atleast 1 digit, and atleast 1 special character")
    private String accPassword;
    @Pattern(regexp = "\\d{4}", message = "Transaction PIN must be exactly 4 digits")
    @NotNull(message = "Transaction PIN cannot be empty")
    private String transactionPin;
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    @NotNull(message = "Contact number cannot be empty!")
    private String contactNumber;
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}", message = "Invalid email address")
    @NotNull(message = "Email cannot be empty!")
    private String emailId;
    private Double accBalance = 0.00;

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public String getAccHolderName() {
        return accHolderName;
    }

    public void setAccHolderName(String accHolderName) {
        this.accHolderName = accHolderName;
    }

    public String getAccHolderLocation() {
        return accHolderLocation;
    }

    public void setAccHolderLocation(String accHolderLocation) {
        this.accHolderLocation = accHolderLocation;
    }

    public String getAccPassword() {
        return accPassword;
    }

    public void setAccPassword(String accPassword) {
        this.accPassword = accPassword;
    }

    public String getTransactionPin() {
        return transactionPin;
    }

    public void setTransactionPin(String transactionPin) {
        this.transactionPin = transactionPin;
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

    public Double getAccBalance() {
        return accBalance;
    }

    public void setAccBalance(Double accBalance) {
        this.accBalance = accBalance;
    }
}
