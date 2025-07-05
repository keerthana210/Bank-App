package com.keerthana.bank_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Entity
@Validated
public class User {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(unique = true)
    private String accNumber;

    private String accHolderName;
    private String accHolderLocation;
    private String accPassword;
    private double accBalance;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    private String contactNumber;

    @Email(message = "Invalid email address")
    private String emailId;

    public double getAccBalance() {
        return accBalance;
    }

    public void setAccBalance(double accBalance) {
        this.accBalance = accBalance;
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public User(int userId, String accNumber, String accHolderName, String accHolderLocation, String accPassword, double accBalance, String contactNumber, String emailId) {

        this.userId = userId;
        this.accNumber = accNumber;
        this.accHolderName = accHolderName;
        this.accHolderLocation = accHolderLocation;
        this.accPassword = accPassword;
        this.accBalance = accBalance;
        this.contactNumber = contactNumber;
        this.emailId = emailId;
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
                ", contactNumber='" + contactNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}
