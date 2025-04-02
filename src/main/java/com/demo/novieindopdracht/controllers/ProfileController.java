package com.demo.novieindopdracht.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping
    public ResponseEntity<?> getProfileById(@PathVariable String id) {
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }
}
