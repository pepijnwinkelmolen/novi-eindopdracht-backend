package com.demo.novieindopdracht.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class AdvertisementInputDto {
    @NotBlank
    public String category;
    @NotBlank
    @Size(min = 6, max = 30)
    public String title;
    public String description;
    @NotNull
    public Double price;
    @NotNull
    public MultipartFile image;
    public String details;
    public String state;
    public String hasToGo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public void setCategory(@NotBlank String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setHasToGo(String hasToGo) {
        this.hasToGo = hasToGo;
    }
}
