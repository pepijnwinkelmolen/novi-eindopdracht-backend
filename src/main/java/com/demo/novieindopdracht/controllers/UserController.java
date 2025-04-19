package com.demo.novieindopdracht.controllers;

import com.demo.novieindopdracht.dtos.NewPasswordDto;
import com.demo.novieindopdracht.dtos.NewUserDto;
import com.demo.novieindopdracht.dtos.NewUsernameDto;
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

    @GetMapping("/login")
    public ResponseEntity<UserOutputDto> loginUser(@RequestHeader(name = "Authorization") @Valid @NotBlank String token) {
        UserOutputDto item = userService.loginUser(token);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid @NotNull NewUserDto newUserDto) {
        userService.createUser(newUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping("/update/username")
    public ResponseEntity<?> updateUsername(@RequestHeader(name = "Authorization") @Valid @NotBlank String token, @RequestBody @Valid NewUsernameDto newUsernameDto) {
        userService.setNewUsername(token, newUsernameDto.username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @PutMapping("/update/password")
    public ResponseEntity<?> updatePassword(@RequestHeader(name = "Authorization") @Valid @NotBlank String token, @RequestBody @Valid NewPasswordDto newPasswordDto) {
        userService.setNewPassword(token, newPasswordDto.password);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@RequestHeader(name = "Authorization") @Valid @NotBlank String token, @PathVariable(name = "id") @Valid long id) {
        userService.deleteUserById(token, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
