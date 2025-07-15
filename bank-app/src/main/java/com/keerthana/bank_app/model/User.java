package com.keerthana.bank_app.model;

import com.keerthana.bank_app.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;

@Entity
@Validated
public class User {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true,name = "acc_number")
    @NotNull(message = "Account number cannot be empty!")
    private String accNumber;
    @NotNull(message = "Account holder name cannot be empty!")
    private String accHolderName;
    @NotNull(message = "Account holder location cannot be empty!")
    private String accHolderLocation;
    @NotNull(message = "Account password cannot be empty!")
    private String accPassword;
    private double accBalance = 0.00;
    @NotNull(message = "Transaction pin cannot be empty!")
    private String transactionPin;
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    @NotNull(message = "Contact number cannot be empty!")
    private String contactNumber;
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}",message = "Invalid email address")
    @NotNull(message = "Email cannot be empty!")
    private String emailId;
    @Enumerated(EnumType.STRING)
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @PrePersist
    public void setRole(){
        this.role=Role.ROLE_USER;
    }

    public String getTransactionPin() {

        return transactionPin;
    }

    public void setTransactionPin(String transactionPin) {

        this.transactionPin = transactionPin;
    }


    public double getAccBalance() {
        return accBalance;
    }

    public void setAccBalance(double accBalance) {
        this.accBalance = accBalance;
    }



    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

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

    public User() {
    }

    public User(Long userId, String accNumber, String accHolderName, String accHolderLocation, String accPassword, double accBalance, String transactionPin, String contactNumber, String emailId, Role role) {
        this.userId = userId;
        this.accNumber = accNumber;
        this.accHolderName = accHolderName;
        this.accHolderLocation = accHolderLocation;
        this.accPassword = accPassword;
        this.accBalance = accBalance;
        this.transactionPin = transactionPin;
        this.contactNumber = contactNumber;
        this.emailId = emailId;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", accNumber='" + accNumber + '\'' +
                ", accHolderName='" + accHolderName + '\'' +
                ", accHolderLocation='" + accHolderLocation + '\'' +
                ", accPassword='" + accPassword + '\'' +
                ", accBalance=" + accBalance +
                ", transactionPin='" + transactionPin + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
