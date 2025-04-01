package com.demo.novieindopdracht.controllers;

import com.demo.novieindopdracht.dtos.AdvertisementInputDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/advertise")
public class AdvertisementController {

    @PostMapping
    public ResponseEntity<?> createAdvert(@RequestBody AdvertisementInputDto advertisementInputDto) {
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAdvert() {
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}