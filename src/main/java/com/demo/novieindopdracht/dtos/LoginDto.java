package com.demo.novieindopdracht.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginDto {
    @NotBlank
    @Size(min = 6, max = 15)
    public String username;
    @NotBlank
    @Size(min = 6, max = 20)
    public String password;

}
