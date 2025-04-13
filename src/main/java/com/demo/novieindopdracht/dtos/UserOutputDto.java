package com.demo.novieindopdracht.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class UserOutputDto {
    @NotNull
    public Long userId;
    @NotBlank
    public String username;
    @NotEmpty
    public List<String> roles;
}
