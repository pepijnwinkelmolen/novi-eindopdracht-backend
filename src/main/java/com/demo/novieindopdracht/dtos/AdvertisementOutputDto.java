package com.demo.novieindopdracht.dtos;

import java.time.LocalDate;
import java.util.List;

public class AdvertisementOutputDto {
    public Long advertisementId;
    public String title;
    public String description;
    public Double price;
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
