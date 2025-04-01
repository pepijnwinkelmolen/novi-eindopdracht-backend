package com.demo.novieindopdracht.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class HomepageController {

    @GetMapping
    public ResponseEntity<?> getSearchedItems(@RequestParam String search) {
        return new ResponseEntity<>("Hier de output", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAdvertisements() {
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getCategories() {
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getFilteredAdvertisements(@RequestParam String price, @RequestParam(name = "since", required = false) String since, @RequestParam(name = "hasToGo", required = false) String hasToGo) {
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
