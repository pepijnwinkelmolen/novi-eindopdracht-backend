package com.demo.novieindopdracht.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BidOutputDto {
    @NotNull
    public Long bidId;
    @NotNull
    public Double price;
    @NotBlank
    public String username;
}
