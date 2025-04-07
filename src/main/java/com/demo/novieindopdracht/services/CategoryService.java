package com.demo.novieindopdracht.services;

import com.demo.novieindopdracht.dtos.CategoryOutputDto;
import com.demo.novieindopdracht.exceptions.ResourceNotFoundException;
import com.demo.novieindopdracht.mappers.CategoryMapper;
import com.demo.novieindopdracht.models.Category;
import com.demo.novieindopdracht.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepos;

    public CategoryService(CategoryRepository categoryRepos) {
        this.categoryRepos = categoryRepos;
    }

    public List<CategoryOutputDto> getCategories() {
        List<Category> categories = categoryRepos.findAll();
        if(!categories.isEmpty()) {
            return CategoryMapper.toDtoList(categories);
        } else {
            throw new ResourceNotFoundException("No categories found");
        }
    }
}
