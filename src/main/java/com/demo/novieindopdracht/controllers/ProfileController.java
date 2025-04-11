package com.demo.novieindopdracht.controllers;

import com.demo.novieindopdracht.dtos.ProfileOutputDto;
import com.demo.novieindopdracht.services.ProfileService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    //done
    @GetMapping("/{id}")
    public ResponseEntity<ProfileOutputDto> getProfile(@RequestHeader(name = "Authorization") @Valid @NotNull @NotBlank String token, @PathVariable @NotNull Long id) {
        ProfileOutputDto profileOutputDto = profileService.getProfile(token, id);
        return new ResponseEntity<>(profileOutputDto, HttpStatus.OK);
    }
}
