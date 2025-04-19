package com.demo.novieindopdracht.controllers;

import com.demo.novieindopdracht.dtos.CategoryOutputDto;
import com.demo.novieindopdracht.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryOutputDto>> getCategories() {
        List<CategoryOutputDto> categoryOutputDtoList = categoryService.getCategories();
        return new ResponseEntity<>(categoryOutputDtoList, HttpStatus.OK);
    }
}
