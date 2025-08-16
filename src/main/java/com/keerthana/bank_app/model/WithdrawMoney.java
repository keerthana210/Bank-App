package com.keerthana.bank_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;

public class WithdrawMoney {
    @JsonIgnore
    private String accNumber;
    @NotNull(message = "Amount cannot be empty")
    private double amount;
    @NotNull(message = "Transaction pin cannot be empty")
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
