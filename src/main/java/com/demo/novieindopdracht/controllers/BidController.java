package com.demo.novieindopdracht.controllers;

import com.demo.novieindopdracht.dtos.BidInputDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/bids")
public class BidController {

    @PostMapping("/{value}/advertisement/{id}/user/{userId}")
    public ResponseEntity<?> createBidOnAdvert(@PathVariable String id, @PathVariable String value, @PathVariable String userId) {
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }
}
