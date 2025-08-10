package com.keerthana.bank_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DepositMoney {
    @JsonIgnore
    private String accNumber;
    private double amountDeposited;
    private String accTransactionPin;

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public double getAmountDeposited() {
        return amountDeposited;
    }

    public void setAmountDeposited(double amountDeposited) {
        this.amountDeposited = amountDeposited;
    }

    public String getAccTransactionPin() {
        return accTransactionPin;
    }

    public void setAccTransactionPin(String accTransactionPin) {
        this.accTransactionPin = accTransactionPin;
    }
}
