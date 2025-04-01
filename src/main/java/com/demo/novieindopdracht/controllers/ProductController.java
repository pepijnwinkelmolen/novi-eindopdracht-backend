package com.demo.novieindopdracht.controllers;

import com.demo.novieindopdracht.dtos.BidInputDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {


    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable long id) {
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createBidOnAdvert(@RequestBody BidInputDto bidInputDto) {
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }
}