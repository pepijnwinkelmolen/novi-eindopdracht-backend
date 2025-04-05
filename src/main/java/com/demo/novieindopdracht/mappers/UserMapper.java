package com.demo.novieindopdracht.mappers;

import com.demo.novieindopdracht.dtos.NewUserDto;
import com.demo.novieindopdracht.dtos.UserInputDto;
import com.demo.novieindopdracht.dtos.UserOutputDto;
import com.demo.novieindopdracht.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {
    public static UserOutputDto toDto(User user) {
        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.userId = user.getUserId();
        userOutputDto.username = user.getUsername();
        return userOutputDto;
    }

    public static User toEntity(UserInputDto userInputDto) {
        User user = new User();
        user.setUserId(userInputDto.userId);
        user.setUsername(userInputDto.username);
        user.setPassword(userInputDto.password);
        return user;
    }

    public static User newUserToEntity(NewUserDto newUserDto) {
        User user = new User();
        user.setUsername(newUserDto.username);
        user.setPassword(newUserDto.password);
        user.setTos(newUserDto.tos);
        user.setPrpolicy(newUserDto.prPolicy);
        return user;
    }
}