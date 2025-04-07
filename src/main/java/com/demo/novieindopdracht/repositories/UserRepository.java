package com.demo.novieindopdracht.repositories;

import com.demo.novieindopdracht.models.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(@Valid @NotBlank @NotNull String username);

    Optional<User> findByUserId(@Valid long id);

    void deleteByUserId(@Valid long id);
}
