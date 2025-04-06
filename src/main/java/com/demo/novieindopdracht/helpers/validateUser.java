package com.demo.novieindopdracht.helpers;

import com.demo.novieindopdracht.exceptions.BadRequestException;
import com.demo.novieindopdracht.models.User;
import com.demo.novieindopdracht.repositories.UserRepository;
import com.demo.novieindopdracht.security.JwtService;

import java.util.Optional;

public class validateUser {

    public static boolean validateUserWithToken(String token, JwtService jwtService, UserRepository userRepos) {
        if (token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");
            if (!jwtService.isTokenExpired(token)) {
                Optional<User> user = userRepos.findByUsername(jwtService.extractUsername(token));
                if (user.isPresent()) {
                    return true;
                } else {
                    throw new BadRequestException("Invalid token");
                }
            } else {
                throw new BadRequestException("Invalid token");
            }
        } else {
            throw new BadRequestException("Invalid token");
        }
    }
}
