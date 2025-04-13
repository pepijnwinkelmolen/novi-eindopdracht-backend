package com.demo.novieindopdracht.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProfileOutputDto {
    @NotNull
    public Long profileId;
    @NotBlank
    public String residence;
    @NotBlank
    public String phoneNumber;
    @Email
    public String email;
    @NotBlank
    public String username;
}
