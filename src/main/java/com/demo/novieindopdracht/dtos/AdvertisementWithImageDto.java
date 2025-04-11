package com.demo.novieindopdracht.dtos;

import java.time.LocalDate;
import java.util.List;

public class AdvertisementWithImageDto {
    public Long advertisementId;
    public String title;
    public String description;
    public Double price;
    public byte[] image;
    public String details;
    public String state;
    public LocalDate date;
    public String hasToGo;
    public List<BidOutputDto> bids;
    public Long userId;

    public List<BidOutputDto> getBids() {
        return bids;
    }

    public void setBids(List<BidOutputDto> bids) {
        this.bids = bids;
    }
}
