package com.demo.novieindopdracht.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NewUserDto {
    @NotBlank
    @Size(min = 6, max = 15)
    public String username;
    @NotBlank
    @Size(min = 6, max = 20)
    public String password;
    @NotBlank
    @Size(min = 3, max = 25)
    public String residence;
    @NotBlank
    @Size(min = 9, max = 15)
    public String phoneNumber;
    @NotBlank
    @Size(min = 11, max = 30)
    public String email;
    public String tos;
    public String prPolicy;
}
