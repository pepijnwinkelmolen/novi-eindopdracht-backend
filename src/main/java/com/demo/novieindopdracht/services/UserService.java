package com.demo.novieindopdracht.services;

import com.demo.novieindopdracht.dtos.NewUserDto;
import com.demo.novieindopdracht.dtos.UserOutputDto;
import com.demo.novieindopdracht.exceptions.BadRequestException;
import com.demo.novieindopdracht.exceptions.ResourceNotFoundException;
import com.demo.novieindopdracht.mappers.ProfileMapper;
import com.demo.novieindopdracht.mappers.UserMapper;
import com.demo.novieindopdracht.models.Profile;
import com.demo.novieindopdracht.models.Role;
import com.demo.novieindopdracht.models.User;
import com.demo.novieindopdracht.repositories.ProfileRepository;
import com.demo.novieindopdracht.repositories.RoleRepository;
import com.demo.novieindopdracht.repositories.UserRepository;
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

    public UserService(UserRepository userRepos, RoleRepository roleRepos, ProfileRepository profileRepos, PasswordEncoder passwordEncoder) {
        this.userRepos = userRepos;
        this.roleRepos = roleRepos;
        this.profileRepos = profileRepos;
        this.passwordEncoder = passwordEncoder;
    }

    public UserOutputDto getUserByUsername(@Valid @NotBlank @NotNull String username) {
        Optional<User> user = userRepos.findByUsername(username);
        if (user.isPresent()) {
            return UserMapper.toDto(user.get());
        } else {
            throw new ResourceNotFoundException("No user found");
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
}
