package com.demo.novieindopdracht.controllers;

import com.demo.novieindopdracht.services.BidService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/bids")
public class BidController {

    private final BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @PostMapping("/{value}/advertisement/{id}")
    public ResponseEntity<?> createBidOnAdvert(@RequestHeader(name = "Authorization") @Valid @NotBlank String token,
                                               @PathVariable @NotNull Long id,
                                               @PathVariable @NotNull @Max(999999999) @Min(1) Double value) {
        bidService.createBidOnAdvert(token, value, id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
