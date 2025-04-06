package com.demo.novieindopdracht.mappers;

import com.demo.novieindopdracht.dtos.NewUserDto;
import com.demo.novieindopdracht.dtos.ProfileOutputDto;
import com.demo.novieindopdracht.models.Profile;

public class ProfileMapper {
    public static Profile newProfileToEntity(NewUserDto newUserDto) {
        Profile profile = new Profile();
        profile.setResidence(newUserDto.residence);
        profile.setPhoneNumber(newUserDto.phoneNumber);
        profile.setEmail(newUserDto.email);
        return profile;
    }

    public static ProfileOutputDto toDto(Profile profile) {
        ProfileOutputDto profileOutputDto = new ProfileOutputDto();
        profileOutputDto.profileId = profile.getProfileId();
        profileOutputDto.username = profile.getUser().getUsername();
        profileOutputDto.residence = profile.getResidence();
        profileOutputDto.phoneNumber = profile.getPhoneNumber();
        profileOutputDto.email = profile.getEmail();
        return profileOutputDto;
    }
}
