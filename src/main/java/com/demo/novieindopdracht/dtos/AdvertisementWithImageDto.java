package com.demo.novieindopdracht.dtos;

import java.time.LocalDate;

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
}
