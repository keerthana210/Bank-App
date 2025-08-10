package com.keerthana.bank_app.model;

public class ViewProfile {
    private String accHolderName;
    private String accHolderLocation;
    private String userId;
    private String contactNumber;
    private String emailId;
    private String accNumber;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public ViewProfile(String accHolderName, String accHolderLocation, String userId, String contactNumber, String emailId, String accNumber) {
        this.accHolderName = accHolderName;
        this.accHolderLocation = accHolderLocation;
        this.userId = userId;
        this.contactNumber = contactNumber;
        this.emailId = emailId;
        this.accNumber = accNumber;
    }
}
