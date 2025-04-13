package com.demo.novieindopdracht.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NewUsernameDto {
    @NotBlank
    @Size(min = 6, max = 15)
    public String username;
}
