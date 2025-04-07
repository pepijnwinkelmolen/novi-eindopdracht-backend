package com.demo.novieindopdracht.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/bids")
public class BidController {

    //needs to be made
    @PostMapping("/{value}/advertisement/{id}")
    public ResponseEntity<?> createBidOnAdvert(@PathVariable String id, @PathVariable String value, @PathVariable String userId) {
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }
}
