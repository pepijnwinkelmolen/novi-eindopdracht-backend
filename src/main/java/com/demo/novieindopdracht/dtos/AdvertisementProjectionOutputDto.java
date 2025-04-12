package com.demo.novieindopdracht.dtos;

public class AdvertisementProjectionOutputDto {
    public Long advertisementId;
    public String title;
    public Double price;
    public org.springframework.core.io.Resource image;

    public void setImage(org.springframework.core.io.Resource image) {
        this.image = image;
    }


}
