package com.demo.novieindopdracht.controllers;

import com.demo.novieindopdracht.dtos.NewUserDto;
import com.demo.novieindopdracht.dtos.UserOutputDto;
import com.demo.novieindopdracht.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    //done
    @GetMapping("/login")
    public ResponseEntity<UserOutputDto> loginUser(@RequestHeader(name = "Authorization") @Valid @NotNull @NotBlank String token) {
        UserOutputDto item = userService.loginUser(token);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    //done
    @Transactional
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid @NotNull NewUserDto newUserDto) {
        userService.createUser(newUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //done needs testing
    @Transactional
    @PutMapping("/update/username")
    public ResponseEntity<?> updateUsername(@RequestHeader(name = "Authorization") @Valid @NotNull @NotBlank String token, @RequestBody @Valid @NotNull @NotBlank String username) {
        userService.setNewUsername(token, username.substring(1, username.length() - 1));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //done
    @Transactional
    @PutMapping("/update/password")
    public ResponseEntity<?> updatePassword(@RequestHeader(name = "Authorization") @Valid @NotNull @NotBlank String token, @RequestBody @Valid @NotNull @NotBlank String password) {
        userService.setNewPassword(token, password.substring(1, password.length() - 1));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //done needs testing
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@RequestHeader(name = "Authorization") @Valid @NotNull @NotBlank String token, @PathVariable(name = "id") @Valid long id) {
        userService.deleteUserById(token, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
