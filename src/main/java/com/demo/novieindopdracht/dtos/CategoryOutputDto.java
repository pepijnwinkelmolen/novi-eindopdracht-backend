package com.demo.novieindopdracht.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CategoryOutputDto {
    @NotNull
    public Long categoryId;
    @NotNull
    public Long parentId;
    @NotBlank
    public String title;
}
