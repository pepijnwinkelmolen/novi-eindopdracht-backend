package com.demo.novieindopdracht.services;

import com.demo.novieindopdracht.dtos.ProfileOutputDto;
import com.demo.novieindopdracht.exceptions.BadRequestException;
import com.demo.novieindopdracht.exceptions.ResourceNotFoundException;
import com.demo.novieindopdracht.helpers.validateUser;
import com.demo.novieindopdracht.mappers.ProfileMapper;
import com.demo.novieindopdracht.models.Profile;
import com.demo.novieindopdracht.models.Role;
import com.demo.novieindopdracht.models.User;
import com.demo.novieindopdracht.repositories.ProfileRepository;
import com.demo.novieindopdracht.repositories.UserRepository;
import com.demo.novieindopdracht.security.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProfileService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final JwtService jwtService;

    public ProfileService(UserRepository userRepository, ProfileRepository profileRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.jwtService = jwtService;
    }

    public ProfileOutputDto getProfile(@Valid @NotNull @NotBlank String token, @NotNull Long id) {
        try {
            if (validateUser.validateUserWithToken(token, jwtService, userRepository)) {
                token = token.replace("Bearer ", "");
                Optional<User> opUser = userRepository.findByUsername(jwtService.extractUsername(token));
                if (opUser.isPresent()) {
                    List<Role> roles = opUser.get().getRoles();
                    String myRole = "user";
                    for(Role value : roles) {
                        String role = value.getRole();
                        if (Objects.equals(role, "ROLE_ADMIN")) {
                            myRole = "admin";
                            break;
                        }
                    }
                    Optional<Profile> profile = profileRepository.findByProfileId(id);
                    if(profile.isPresent()) {
                        if(myRole.equals("admin")) {
                            return ProfileMapper.toDto(profile.get());
                        } else {
                            if(Objects.equals(profile.get().getProfileId(), opUser.get().getProfile().getProfileId())) {
                                return ProfileMapper.toDto(opUser.get().getProfile());
                            } else {
                                throw new AuthenticationException("Invalid user");
                            }
                        }
                    } else {
                        throw new ResourceNotFoundException("No such profile");
                    }
                } else {
                    throw new ResourceNotFoundException("Profile not found");
                }
            } else {
                throw new BadRequestException("Invalid token");
            }
        } catch (Exception err) {
            throw new BadRequestException("Invalid token");
        }
    }
}
