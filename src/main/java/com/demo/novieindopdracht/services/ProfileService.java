package com.demo.novieindopdracht.services;

import com.demo.novieindopdracht.dtos.ProfileOutputDto;
import com.demo.novieindopdracht.exceptions.BadRequestException;
import com.demo.novieindopdracht.exceptions.ResourceNotFoundException;
import com.demo.novieindopdracht.helpers.validateUser;
import com.demo.novieindopdracht.mappers.ProfileMapper;
import com.demo.novieindopdracht.models.Profile;
import com.demo.novieindopdracht.models.User;
import com.demo.novieindopdracht.repositories.UserRepository;
import com.demo.novieindopdracht.security.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public ProfileService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public ProfileOutputDto getProfile(@Valid @NotNull @NotBlank String token) {
        try {
            if (validateUser.validateUserWithToken(token, jwtService, userRepository)) {
                token = token.replace("Bearer ", "");
                Optional<User> opUser = userRepository.findByUsername(jwtService.extractUsername(token));
                if (opUser.isPresent()) {
                    Profile profile = opUser.get().getProfile();
                    return ProfileMapper.toDto(profile);
                } else {
                    throw new ResourceNotFoundException("Profile not found.");
                }
            } else {
                throw new BadRequestException("Invalid token");
            }
        } catch (Exception err) {
            throw new BadRequestException("Invalid token");
        }
    }
}
