package com.demo.novieindopdracht.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @PutMapping
    public ResponseEntity<?> updateUsername() {
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<?> updatePassword() {
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}
