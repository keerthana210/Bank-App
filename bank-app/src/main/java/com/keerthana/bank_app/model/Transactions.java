package com.keerthana.bank_app.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String senderAccNum;
    private String receiverAccNum;
    private double amount;
    private LocalDateTime timestamp;

    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Transactions() {
    }

    public Transactions(long id, String senderAccNum, String receiverAccNum, double amount, LocalDateTime timestamp, String status, String message) {
        this.id = id;
        this.senderAccNum = senderAccNum;
        this.receiverAccNum = receiverAccNum;
        this.amount = amount;
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "id=" + id +
                ", senderAccNum='" + senderAccNum + '\'' +
                ", receiverAccNum='" + receiverAccNum + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    @PrePersist
    public void setTimestamp() {
        this.timestamp = LocalDateTime.now();
    }

}
