package com.example.payment;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Payment implements Serializable {

    @Exclude private  String id;

    private String payer;
    private String receiver;
    private String amount;
    private String currency;
    private String date;
    private String method;

    public Payment(){}

    public Payment(String payer, String receiver, String amount, String currency, String date, String method) {
        this.payer = payer;
        this.receiver = receiver;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.method = method;
    }


    public String getPayer() { return payer; }
    public String getReceiver() { return receiver; }
    public String getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getDate() { return date; }
    public String getMethod() { return method; }
    public String getId() { return id; }

    public void setPayer(String payer) { this.payer = payer; }
    public void setReceiver(String receiver) { this.receiver = receiver; }
    public void setAmount(String amount) { this.amount = amount; }
    public void setCurrency(String currency) { this.currency = currency; }
    public void setDate(String date) { this.date = date; }
    public void setMethod(String method) { this.method = method; }
    public void setId(String id) { this.id = id; }
}
