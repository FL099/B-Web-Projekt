package com.example.projekt.dto;

import com.example.projekt.util.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Positive;
import java.util.Date;

public class OfferDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer Id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer auctionId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer creatorId;

    @Positive( message = "price muss positiv sein")
    private int price;

    @Positive( message = "amount muss positiv sein")
    private int amount;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date deliveryDate;
    //TODO: fixen

    private State state;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
