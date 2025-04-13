package com.demo.novieindopdracht.controllers;

import com.demo.novieindopdracht.dtos.*;
import com.demo.novieindopdracht.mappers.AdvertisementMapper;
import com.demo.novieindopdracht.services.AdvertisementService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

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
    public ResponseEntity<List<AdvertisementProjectionWithImageDto>> getAdvertisements() throws IOException {
        List<AdvertisementProjectionOutputDto> items = advertisementService.getAllAdvertisements();
        List<AdvertisementProjectionWithImageDto> advertisementProjectionWithImageDtoList = AdvertisementMapper.toProjectionImageDtoList(items);
        return new ResponseEntity<>(advertisementProjectionWithImageDtoList, HttpStatus.OK);
    }

    //done
    @GetMapping("/search")
    public ResponseEntity<List<AdvertisementWithImageDto>> getSearchedAdvertisements(@RequestParam(name="query") @Valid @Size(min = 3, max = 20)
                                                                                      @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "Not a valid input") String query) throws IOException {
        List<AdvertisementOutputDto> items = advertisementService.getAllAdvertisementsLikeQuery(query);
        List<AdvertisementWithImageDto> advertisementWithImageDtoList = AdvertisementMapper.toImageDtoList(items);
        return new ResponseEntity<>(advertisementWithImageDtoList, HttpStatus.OK);
    }

    //done
    @GetMapping("/filter")
    public ResponseEntity<List<AdvertisementWithImageDto>> getFilteredAdvertisements(@RequestParam(name="price") @Valid @Min(0) @Max(250) Double price,
                                                       @RequestParam(name="since", required = false) @Valid String since,
                                                       @RequestParam(name="has-to-go", required = false) @Valid String hasToGo) throws IOException {
        List<AdvertisementOutputDto> items = advertisementService.getAllAdvertisementsWithFilter(price, since, hasToGo);
        List<AdvertisementWithImageDto> advertisementWithImageDtoList = AdvertisementMapper.toImageDtoList(items);
        return new ResponseEntity<>(advertisementWithImageDtoList, HttpStatus.OK);
    }

    //done
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAdvertisementById(@PathVariable(name = "id") @Valid long id) throws IOException {
        AdvertisementOutputDto item = advertisementService.getAdvertisementById(id);
        AdvertisementWithImageDto advertisementWithImageDto = AdvertisementMapper.toImageDto(item);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + item.image.getFilename())
                .body(advertisementWithImageDto);
    }

    //done
    @GetMapping("/category/{category}")
    public ResponseEntity<List<AdvertisementWithImageDto>> getAdvertisementsByCategory(@PathVariable(name = "category") @Valid String category) throws IOException {
        List<AdvertisementOutputDto> items = advertisementService.getAllAdvertisementsByCategory(category);
        List<AdvertisementWithImageDto> advertisementWithImageDtoList = AdvertisementMapper.toImageDtoList(items);
        return new ResponseEntity<>(advertisementWithImageDtoList, HttpStatus.OK);
    }

    //done
    @Transactional
    @PostMapping
    public ResponseEntity<String> createAdvert(@RequestHeader(name = "Authorization") @Valid @NotBlank String token,
                                                               @ModelAttribute AdvertisementInputDto advertisementInputDto) {
        System.out.println(advertisementInputDto.category);
        Long id = advertisementService.createAdvert(token, advertisementInputDto);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/product/")
                .path(Objects.requireNonNull(id.toString()))
                .toUriString();
        return ResponseEntity.created(URI.create(url)).body("Uw advertentie is aangemaakt. ID = " + id);
    }

    //done
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdvert(@RequestHeader(name = "Authorization") @Valid @NotBlank String token, @PathVariable(name = "id") @Valid long id) {
        advertisementService.deleteAdvert(token, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}