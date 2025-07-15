package com.keerthana.bank_app.model;

import com.keerthana.bank_app.configuration.SecurityConfig;
import com.keerthana.bank_app.enums.AdminAccessLevel;
import com.keerthana.bank_app.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

@Entity
@Validated
public class Admin {


    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @NotNull(message = "Name cannot be empty!")
    private String adminName;
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    @NotNull(message = "Contact Number cannot be empty!")
    private String contactNumber;
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}",message = "Invalid email address")
    @NotNull(message = "Email cannot be empty!")
    private String emailId;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Access Level cannot be empty!")
    private AdminAccessLevel access;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String adminPassword;

    public Admin() {
    }

    public Admin(Long adminId, String adminName, String contactNumber, String emailId, AdminAccessLevel access, Role role, String adminPassword) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.contactNumber = contactNumber;
        this.emailId = emailId;
        this.access = access;
        this.role = role;
        this.adminPassword = adminPassword;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
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

    public AdminAccessLevel getAccess() {
        return access;
    }

    public void setAccess(AdminAccessLevel access) {
        this.access = access;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", adminName='" + adminName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                ", access=" + access +
                ", role=" + role +
                ", adminPassword='" + adminPassword + '\'' +
                '}';
    }

    @PrePersist
    public void setAdminPassword(){
        this.adminPassword="Admin@123";
    }


}
