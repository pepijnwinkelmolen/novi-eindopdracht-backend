package com.demo.novieindopdracht.controllers;

import com.demo.novieindopdracht.dtos.NewUserDto;
import com.demo.novieindopdracht.dtos.UserOutputDto;
import com.demo.novieindopdracht.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserOutputDto> getUserByUsername(@PathVariable @Valid @NotBlank
                                                               @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "Not a valid input") String username) {
        UserOutputDto item = userService.getUserByUsername(username);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid @NotNull NewUserDto newUserDto) {
        userService.createUser(newUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}/username")
    public ResponseEntity<?> updateUsername(@PathVariable(name = "id") @Valid @NotNull Long id, String username) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, String password) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
