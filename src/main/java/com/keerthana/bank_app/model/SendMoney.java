package com.keerthana.bank_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class SendMoney {
    @JsonIgnore
    private String senderAccNum;
    @NotNull(message = "Receiver account number cannot be empty")
    private String receiverAccNum;
    @NotNull(message = "Transaction pin cannot be empty")
    private String senderTransactionPin;
    @NotNull(message = "Amount cannot be empty")
    private double amountSent;
    private LocalDateTime date;

    public SendMoney() {
    }

    public SendMoney(String senderAccNum, String receiverAccNum, String senderTransactionPin, double amountSent, LocalDateTime date) {
        this.senderAccNum = senderAccNum;
        this.receiverAccNum = receiverAccNum;
        this.senderTransactionPin = senderTransactionPin;
        this.amountSent = amountSent;
        this.date = date;
    }


    public String getSenderAccNum() {
        return senderAccNum;
    }

    public void setSenderAccNum(String senderAccNum) {
        this.senderAccNum = senderAccNum;
    }

    public String getReceiverAccNum() {
        return receiverAccNum;
    }

    public void setReceiverAccNum(String receiverAccNum) {
        this.receiverAccNum = receiverAccNum;
    }

    public String getSenderTransactionPin() {
        return senderTransactionPin;
    }

    public void setSenderTransactionPin(String senderAccPin) {
        this.senderTransactionPin = senderAccPin;
    }

    public double getAmountSent() {
        return amountSent;
    }

    public void setAmountSent(double amountSent) {
        this.amountSent = amountSent;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SendMoney{" +
                "senderAccNum='" + senderAccNum + '\'' +
                ", receiverAccNum='" + receiverAccNum + '\'' +
                ", senderAccPin='" + senderTransactionPin + '\'' +
                ", amountSent='" + amountSent + '\'' +
                ", date=" + date +
                '}';
    }

    @PrePersist
    public void prePersist() {
        this.date = LocalDateTime.now();
    }

}
