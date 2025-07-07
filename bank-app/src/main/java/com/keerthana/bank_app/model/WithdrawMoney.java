package com.keerthana.bank_app.model;

public class WithdrawMoney {
    private String accNumber;
    private double amount;
    private String accTransactionPin;

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccTransactionPin() {
        return accTransactionPin;
    }

    public void setAccTransactionPin(String accTransactionPin) {
        this.accTransactionPin = accTransactionPin;
    }
}
