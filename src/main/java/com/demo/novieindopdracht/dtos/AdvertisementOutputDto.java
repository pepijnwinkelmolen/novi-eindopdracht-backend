package com.demo.novieindopdracht.dtos;

import java.io.File;
import java.time.LocalDate;

public class AdvertisementOutputDto {
    public Long advertisementId;
    public Long categoryId;
    public String title;
    public String description;
    public Double price;
    public File image;
    public String details;
    public String state;
    public LocalDate date;
    public String hasToGo;
}
