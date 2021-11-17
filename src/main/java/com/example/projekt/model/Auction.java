package com.example.projekt.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Auction {

    //TODO: "product" von String auf Product-Klasse ändern

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    private Date startTime;

    private Date endTime;

    @NotNull(message = "product muss vorhanden sein")
    private String product;

    @Positive(message = "amount muss >0 sein")
    @NotNull(message = "amount muss vorhanden sein")
    private Integer amount;

    public Auction(String product, Integer amount){
        this.startTime = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(this.startTime);
        c.add(Calendar.MONTH, 1);
        endTime = c.getTime();
        this.product = product;
        this.amount = amount;
    }

    public Auction(Date startTime, Date endTime, String product, Integer amount){
        this.startTime = startTime;
        this.endTime = endTime;
        this.product = product;
        this.amount = amount;
    }

    public Auction(){

    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getAmount() {
        return (amount == null)? amount: 0;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
