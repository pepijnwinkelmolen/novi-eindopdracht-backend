package com.demo.novieindopdracht.dtos;

import java.time.LocalDate;

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

    public void setImage(org.springframework.core.io.Resource image) {
        this.image = image;
    }
}
