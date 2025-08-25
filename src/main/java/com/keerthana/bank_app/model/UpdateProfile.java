package com.keerthana.bank_app.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateProfile {

    @NotNull(message = "Account Holder Name cannot be empty!")
    @NotBlank(message = "Account Holder Name cannot be empty!")
    private String accHolderName;
    @NotNull(message = "Location cannot be empty!")
    @NotNull(message = "Location cannot be empty!")
    private String accHolderLocation;
    @NotNull(message = "Contact number cannot be empty!")
    @NotNull(message = "Contact number cannot be empty!")
    private String contactNumber;
    @NotNull(message = "Email Id cannot be empty!")
    @NotNull(message = "Email Id cannot be empty!")
    @Email(message = "Invalid email format")
    private String emailId;

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
}
