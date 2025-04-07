package com.demo.novieindopdracht.controllers;

import com.demo.novieindopdracht.dtos.AdvertisementInputDto;
import com.demo.novieindopdracht.dtos.AdvertisementOutputDto;
import com.demo.novieindopdracht.dtos.AdvertisementProjectionOutputDto;
import com.demo.novieindopdracht.services.AdvertisementService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@CrossOrigin
@RequestMapping("/advertisements")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    //done
    @GetMapping
    public ResponseEntity<List<AdvertisementProjectionOutputDto>> getAdvertisements() {
        List<AdvertisementProjectionOutputDto> items = advertisementService.getAllAdvertisements();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    //done
    @GetMapping("/search")
    public ResponseEntity<List<AdvertisementOutputDto>> getSearchedAdvertisements(@RequestParam(name="query") @Valid @Size(min = 3, max = 20)
                                                                                      @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "Not a valid input") String query) {
        List<AdvertisementOutputDto> items = advertisementService.getAllAdvertisementsLikeQuery(query);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    //done
    @GetMapping("/filter")
    public ResponseEntity<List<AdvertisementOutputDto>> getFilteredAdvertisements(@RequestParam(name="price") @Valid @Min(0) @Max(100) Double price,
                                                       @RequestParam(name="since", required = false) @Valid String since,
                                                       @RequestParam(name="has-to-go", required = false) @Valid String hasToGo) {
        List<AdvertisementOutputDto> items = advertisementService.getAllAdvertisementsWithFilter(price, since, hasToGo);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    //needs to be checked
    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementOutputDto> getAdvertisementById(@PathVariable(name = "id") @Valid long id) {
        AdvertisementOutputDto item = advertisementService.getAdvertisementById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    //needs testing
    @GetMapping("/category/{category}")
    public ResponseEntity<List<AdvertisementOutputDto>> getAdvertisementsByCategory(@PathVariable(name = "category") @Valid String category) {
        List<AdvertisementOutputDto> items = advertisementService.getAllAdvertisementsByCategory(category);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    //needs to be created
    @Transactional
    @PostMapping
    public ResponseEntity<AdvertisementOutputDto> createAdvert(@RequestBody @Valid @NotNull AdvertisementInputDto advertisementInputDto) {
        AdvertisementOutputDto item = advertisementService.createAdvert(advertisementInputDto);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    //needs authentication
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdvert(@PathVariable(name = "id") @Valid long id) {
        advertisementService.deleteAdvert(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}