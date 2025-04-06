package com.demo.novieindopdracht.repositories;

import com.demo.novieindopdracht.models.Category;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByTitle(@Valid String category);
}
