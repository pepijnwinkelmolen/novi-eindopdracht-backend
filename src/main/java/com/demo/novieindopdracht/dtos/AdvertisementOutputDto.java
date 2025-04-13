package com.demo.novieindopdracht.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class AdvertisementOutputDto {
    @NotNull
    public Long advertisementId;
    @NotBlank
    public String title;
    public String description;
    @NotNull
    public Double price;
    @NotNull
    public org.springframework.core.io.Resource image;
    public String details;
    public String state;
    public LocalDate date;
    public String hasToGo;
    public List<BidOutputDto> bids;
    public Long userId;

    public void setImage(org.springframework.core.io.Resource image) {
        this.image = image;
    }

    public void setBids(List<BidOutputDto> bids) {
        this.bids = bids;
    }

    public List<BidOutputDto> getBids() {
        return bids;
    }
}
