package com.keerthana.bank_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.keerthana.bank_app.enums.Role;
import jakarta.persistence.*;
import org.springframework.validation.annotation.Validated;

@Entity
@Validated
public class User {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String userId;
    @Column(unique = true, nullable = false)
    private String accNumber;
    @Column(nullable = false)
    private String accHolderName;
    @Column(nullable = false)
    private String accHolderLocation;
    @Column(nullable = false)
    @JsonIgnore
    private String accPassword;
    private double accBalance = 0.00;
    @Column(nullable = false)
    @JsonIgnore
    private String transactionPin;
    @Column(unique = true, nullable = false)
    private String contactNumber;
    @Column(unique = true, nullable = false)
    private String emailId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @PrePersist
    public void setRole() {
        this.role = Role.USER;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public double getAccBalance() {
        return accBalance;
    }

    public void setAccBalance(double accBalance) {
        this.accBalance = accBalance;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User() {
    }

    public User(Long id, String userId, String accNumber, String accHolderName, String accHolderLocation, String accPassword, double accBalance, String transactionPin, String contactNumber, String emailId, Role role) {
        this.id = id;
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
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", accNumber='" + accNumber + '\'' +
                ", accHolderName='" + accHolderName + '\'' +
                ", accHolderLocation='" + accHolderLocation + '\'' +
                ", accPassword='" + accPassword + '\'' +
                ", accBalance=" + accBalance +
                ", transactionPin='" + transactionPin + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                ", role=" + role +
                '}';
    }
}
