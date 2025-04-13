package com.demo.novieindopdracht.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AdvertisementProjectionWithImageDto {
    @NotNull
    public Long advertisementId;
    @NotBlank
    public String title;
    @NotNull
    public Double price;
    @NotNull
    public byte[] image;
}
