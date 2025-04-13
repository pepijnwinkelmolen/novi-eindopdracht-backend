package com.demo.novieindopdracht.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class AdvertisementWithImageDto {
    @NotNull
    public Long advertisementId;
    @NotBlank
    public String title;
    public String description;
    @NotNull
    public Double price;
    @NotNull
    public byte[] image;
    public String details;
    public String state;
    public LocalDate date;
    public String hasToGo;
    public List<BidOutputDto> bids;
    @NotNull
    public Long userId;

    public void setBids(List<BidOutputDto> bids) {
        this.bids = bids;
    }
}
