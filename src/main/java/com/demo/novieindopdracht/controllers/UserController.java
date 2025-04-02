package com.demo.novieindopdracht.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<?> createUser() {
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    @PutMapping("/{id}/username")
    public ResponseEntity<?> updateUsername(@PathVariable String id) {
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> updatePassword(@PathVariable String id) {
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}
