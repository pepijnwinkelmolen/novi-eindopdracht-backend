package com.demo.novieindopdracht.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @PostMapping
    public ResponseEntity<?> createUser() {
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }
}
