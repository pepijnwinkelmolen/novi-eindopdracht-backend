package com.demo.novieindopdracht.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AdvertisementProjectionOutputDto {
    @NotNull
    public Long advertisementId;
    @NotBlank
    public String title;
    @NotNull
    public Double price;
    @NotNull
    public org.springframework.core.io.Resource image;

    public void setImage(org.springframework.core.io.Resource image) {
        this.image = image;
    }


}
