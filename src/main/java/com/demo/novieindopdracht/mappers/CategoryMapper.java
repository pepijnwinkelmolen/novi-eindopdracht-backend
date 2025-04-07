package com.demo.novieindopdracht.mappers;

import com.demo.novieindopdracht.dtos.CategoryOutputDto;
import com.demo.novieindopdracht.models.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    public static CategoryOutputDto toDto(Category category) {
        CategoryOutputDto categoryOutputDto = new CategoryOutputDto();
        categoryOutputDto.categoryId = category.getCategoryId();
        categoryOutputDto.parentId = category.getParentId();
        categoryOutputDto.title = category.getTitle();

        return categoryOutputDto;
    }

    public static List<CategoryOutputDto> toDtoList(List<Category> categories) {
        return categories.stream().map(CategoryMapper::toDto).collect(Collectors.toList());
    }
}
