package com.keerthana.bank_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;

@Entity
@Validated
public class Admin {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long adminId;

    private String adminName;
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    private String contactNumber;
    @Email(message = "Invalid email address")
    private String emailId;
    private String access;
    private String adminPassword;

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
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

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public Admin() {
    }

    public Admin(long adminId, String adminName, String contactNumber, String emailId, String access, String adminPassword) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.contactNumber = contactNumber;
        this.emailId = emailId;
        this.access = access;
        this.adminPassword = adminPassword;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", adminName='" + adminName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                ", access='" + access + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                '}';
    }


}
