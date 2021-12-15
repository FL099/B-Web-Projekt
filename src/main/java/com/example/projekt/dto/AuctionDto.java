package com.example.projekt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

public class AuctionDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer Id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer creatorId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String product;

    @Positive
    private Integer minPrice;

    @Positive
    private Integer maxPrice;

    @Positive
    private int minAmount;

    @Positive
    private int maxAmount;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date minDelDate;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date maxDelDate;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startTime;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endTime;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Date getMinDelDate() {
        return minDelDate;
    }

    public void setMinDelDate(Date minDelDate) {
        this.minDelDate = minDelDate;
    }

    public Date getMaxDelDate() {
        return maxDelDate;
    }

    public void setMaxDelDate(Date maxDelDate) {
        this.maxDelDate = maxDelDate;
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
}
