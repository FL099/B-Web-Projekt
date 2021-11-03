package com.example.projekt.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @Positive
    @NotBlank
    private int ammount;

    @Positive
    @NotBlank
    private int price;

    @NotBlank
    private Date deliveryDate;

    public Offer(){

    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public int getAmmount() {
        return ammount;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
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

    public Offer(int ammount, int price){
        this.ammount = ammount;
        this.price = price;
        this.deliveryDate = new Date();
    }

    public Offer(int ammount, int price, String deliveryDate){
        this.ammount = ammount;
        this.price = price;
        try {
            this.deliveryDate = new SimpleDateFormat("dd/mm/yyyy").parse(deliveryDate);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Wrong Date Format");
        }
    }


}
