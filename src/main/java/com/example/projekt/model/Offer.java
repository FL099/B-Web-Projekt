package com.example.projekt.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @NotNull( message = "amount ist notwendig")
    @Positive( message = "amount muss positiv sein")
    private int amount;

    @NotNull( message = "price ist notwendig")
    @Positive( message = "price muss positiv sein")
    private int price;

    @NotNull( message = "deliveryDate ist notwendig")
    private Date deliveryDate;

    //TODO: auskommentieren, wenns keine Probleme macht:
    @NotNull( message = "Offer muss einer Auktion zugeordnet sein")
    private Integer auct; //TODO: wieder in auctionId umbenennen und int machen

    //TODO: auskommentieren, wenns keine Probleme macht: @NotNull( message = "Offer muss einem Ersteller zugeordnet sein")
    private Integer creatorId;

    public Offer(){

    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDeliveryDate() {
        return deliveryDate.toString();
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getAuct() {
        return auct;
    }

    public void setAuct(Integer auct) {
        this.auct = auct;
    }

    public Offer(int amount, int price){
        this.amount = amount;
        this.price = price;
        this.deliveryDate = new Date();
    }

    /*public Offer(int amount, int price, String auctionId){
        this(amount, price);
        this.auctionnum = auctionId;
    }*/

    public Offer(int amount, int price, String deliveryDate){
        this.amount = amount;
        this.price = price;
        try {
            this.deliveryDate = new SimpleDateFormat("dd/mm/yyyy").parse(deliveryDate);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Wrong Date Format");
        }
    }


}
