package com.demo.novieindopdracht.services;

import com.demo.novieindopdracht.dtos.NewUserDto;
import com.demo.novieindopdracht.dtos.UserOutputDto;
import com.demo.novieindopdracht.exceptions.BadRequestException;
import com.demo.novieindopdracht.exceptions.ResourceNotFoundException;
import com.demo.novieindopdracht.helpers.validateUser;
import com.demo.novieindopdracht.mappers.ProfileMapper;
import com.demo.novieindopdracht.mappers.UserMapper;
import com.demo.novieindopdracht.models.Profile;
import com.demo.novieindopdracht.models.Role;
import com.demo.novieindopdracht.models.User;
import com.demo.novieindopdracht.repositories.ProfileRepository;
import com.demo.novieindopdracht.repositories.RoleRepository;
import com.demo.novieindopdracht.repositories.UserRepository;
import com.demo.novieindopdracht.security.JwtService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepos;
    private final RoleRepository roleRepos;
    private final ProfileRepository profileRepos;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepos, RoleRepository roleRepos, ProfileRepository profileRepos, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepos = userRepos;
        this.roleRepos = roleRepos;
        this.profileRepos = profileRepos;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserOutputDto loginUser(@Valid @NotBlank @NotNull String token) {
        if (token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");
            if (!jwtService.isTokenExpired(token)) {
                Optional<User> user = userRepos.findByUsername(jwtService.extractUsername(token));
                if (user.isPresent()) {
                    return UserMapper.toDto(user.get());
                } else {
                    throw new ResourceNotFoundException("No user found");
                }
            } else {
                throw new BadRequestException("Token no longer valid");
            }
        } else {
            throw new BadRequestException("Incorrect token");
        }
    }

    @Transactional
    public void createUser(@Valid @NotNull NewUserDto newUserDto) {
        try {
            User user = UserMapper.newUserToEntity(newUserDto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Profile profile = ProfileMapper.newProfileToEntity(newUserDto);
            profileRepos.save(profile);
            user.setProfile(profileRepos.findByPhoneNumber(profile.getPhoneNumber()));
            List<Role> newRoles = new ArrayList<>();
            Optional<Role> or = roleRepos.findById("ROLE_USER");
            if (or.isPresent()) {
                newRoles.add(or.get());
                user.setRoles(newRoles);
                userRepos.save(user);
            } else {
                throw new BadRequestException("Bad Request");
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Transactional
    public void setNewPassword(String token, String password) {
        try {
            if (password.matches("^[A-Za-z0-9_]+$")) {
                if (validateUser.validateUserWithToken(token, jwtService, userRepos)) {
                    token = token.replace("Bearer ", "");
                    Optional<User> opUser = userRepos.findByUsername(jwtService.extractUsername(token));
                    if (opUser.isPresent()) {
                        User user = opUser.get();
                        user.setPassword(passwordEncoder.encode(password));
                        userRepos.save(user);
                    } else {
                        throw new ResourceNotFoundException("Invalid token");
                    }
                } else {
                    throw new BadRequestException("Invalid token");
                }
            } else {
                throw new BadRequestException("Invalid input");
            }
        } catch (Exception err) {
            throw new BadRequestException(err.getMessage());
        }
    }

    public void setNewUsername(@Valid @NotNull @NotBlank String token, String username) {
        try {
            if (username.matches("^[A-Za-z0-9_]+$")) {
                if (validateUser.validateUserWithToken(token, jwtService, userRepos)) {
                    token = token.replace("Bearer ", "");
                    Optional<User> opUser = userRepos.findByUsername(jwtService.extractUsername(token));
                    if (opUser.isPresent()) {
                        User user = opUser.get();
                        user.setUsername(username);
                        userRepos.save(user);
                    } else {
                        throw new ResourceNotFoundException("Invalid token");
                    }
                } else {
                    throw new BadRequestException("Invalid token");
                }
            } else {
                throw new BadRequestException("Invalid input");
            }
        } catch (Exception err) {
            throw new BadRequestException(err.getMessage());
        }
    }

    public void deleteUserById(@Valid @NotNull @NotBlank String token, @Valid long id) {

    }
}
