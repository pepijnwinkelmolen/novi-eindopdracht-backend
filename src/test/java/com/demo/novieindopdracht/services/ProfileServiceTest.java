package com.demo.novieindopdracht.services;

import com.demo.novieindopdracht.dtos.ProfileOutputDto;
import com.demo.novieindopdracht.exceptions.ResourceNotFoundException;
import com.demo.novieindopdracht.exceptions.AuthenticationException;
import com.demo.novieindopdracht.helpers.ValidateUser;
import com.demo.novieindopdracht.models.Profile;
import com.demo.novieindopdracht.models.Role;
import com.demo.novieindopdracht.models.User;
import com.demo.novieindopdracht.repositories.ProfileRepository;
import com.demo.novieindopdracht.repositories.UserRepository;
import com.demo.novieindopdracht.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    JwtService jwtService;

    @Mock
    ProfileRepository profileRepository;

    @InjectMocks
    ProfileService profileService;

    /* METHOD: getProfile */

    @Test
    void getProfileWithAdminAccess() {
        // Arrange
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";
        String cleanToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";
        Long profileId = 10L;

        Role adminRole = new Role();
        adminRole.setRole("ROLE_ADMIN");

        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setRoles(List.of(adminRole));

        Profile profile = new Profile();
        profile.setProfileId(profileId);
        profile.setEmail("admin@example.com");
        profile.setUser(adminUser);

        try (MockedStatic<ValidateUser> validateUserMock = Mockito.mockStatic(ValidateUser.class)) {
            validateUserMock.when(() ->
                            ValidateUser.validateUserWithToken(token, jwtService, userRepository))
                    .thenReturn(true);

            when(jwtService.extractUsername(cleanToken)).thenReturn("admin");
            when(userRepository.findByUsername("admin")).thenReturn(Optional.of(adminUser));
            when(profileRepository.findByProfileId(profileId)).thenReturn(Optional.of(profile));

            // Act
            ProfileOutputDto result = profileService.getProfile(token, profileId);

            // Assert
            assertEquals(profile.getEmail(), result.email);
            verify(profileRepository).findByProfileId(profileId);
        }
    }

    @Test
    void getProfileWithUserAccessingOwnProfile() {
        // Arrange
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";
        String cleanToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";
        Long profileId = 100L;

        Role userRole = new Role();
        userRole.setRole("ROLE_USER");

        Profile profile = new Profile();
        profile.setProfileId(profileId);
        profile.setEmail("user@example.com");

        User user = new User();
        user.setUsername("user123");
        user.setRoles(List.of(userRole));
        user.setProfile(profile);
        profile.setUser(user);

        try (MockedStatic<ValidateUser> validateUserMock = Mockito.mockStatic(ValidateUser.class)) {
            validateUserMock.when(() ->
                            ValidateUser.validateUserWithToken(token, jwtService, userRepository))
                    .thenReturn(true);

            when(jwtService.extractUsername(cleanToken)).thenReturn("user123");
            when(userRepository.findByUsername("user123")).thenReturn(Optional.of(user));
            when(profileRepository.findByProfileId(profileId)).thenReturn(Optional.of(profile));

            // Act
            ProfileOutputDto result = profileService.getProfile(token, profileId);

            // Assert
            assertEquals("user@example.com", result.email);
        }
    }

    @Test
    void invalidTokenAsInput() {
        // Arrange
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";

        try (MockedStatic<ValidateUser> validateUserMock = Mockito.mockStatic(ValidateUser.class)) {
            validateUserMock.when(() ->
                            ValidateUser.validateUserWithToken(token, jwtService, userRepository))
                    .thenReturn(false);

            // Act & Assert
            AuthenticationException thrown = assertThrows(AuthenticationException.class, () ->
                    profileService.getProfile(token, 1L));

            assertEquals("User unauthorized", thrown.getMessage());
        }
    }

    @Test
    void loggedInUserNotFound() {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";
        String cleanToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";

        try (MockedStatic<ValidateUser> validateUserMock = Mockito.mockStatic(ValidateUser.class)) {
            validateUserMock.when(() ->
                            ValidateUser.validateUserWithToken(token, jwtService, userRepository))
                    .thenReturn(true);

            when(jwtService.extractUsername(cleanToken)).thenReturn("emptyUser");
            when(userRepository.findByUsername("emptyUser")).thenReturn(Optional.empty());

            // Act & Assert
            ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                    profileService.getProfile(token, 1L));

            assertEquals("Profile not found", thrown.getMessage());
        }
    }

    @Test
    void profileNotFound() {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";
        String cleanToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";

        Role userRole = new Role();
        userRole.setRole("ROLE_USER");

        Profile profile = new Profile();
        profile.setProfileId(1L);

        User user = new User();
        user.setUsername("user");
        user.setRoles(List.of(userRole));
        user.setProfile(profile);

        try (MockedStatic<ValidateUser> validateUserMock = Mockito.mockStatic(ValidateUser.class)) {
            validateUserMock.when(() ->
                            ValidateUser.validateUserWithToken(token, jwtService, userRepository))
                    .thenReturn(true);

            when(jwtService.extractUsername(cleanToken)).thenReturn("user");
            when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
            when(profileRepository.findByProfileId(999L)).thenReturn(Optional.empty());

            // Act & Assert
            ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                    profileService.getProfile(token, 999L));

            assertEquals("No such profile", thrown.getMessage());
        }
    }

    @Test
    void userTriesAccessingOtherProfile() {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";
        String cleanToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";

        Role userRole = new Role();
        userRole.setRole("ROLE_USER");

        Profile ownProfile = new Profile();
        ownProfile.setProfileId(1L);

        Profile otherProfile = new Profile();
        otherProfile.setProfileId(2L);

        User user = new User();
        user.setUsername("user");
        user.setRoles(List.of(userRole));
        user.setProfile(ownProfile);

        try (MockedStatic<ValidateUser> validateUserMock = Mockito.mockStatic(ValidateUser.class)) {
            validateUserMock.when(() ->
                            ValidateUser.validateUserWithToken(token, jwtService, userRepository))
                    .thenReturn(true);

            when(jwtService.extractUsername(cleanToken)).thenReturn("user");
            when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
            when(profileRepository.findByProfileId(2L)).thenReturn(Optional.of(otherProfile));

            // Act & Assert
            AuthenticationException thrown = assertThrows(AuthenticationException.class, () ->
                    profileService.getProfile(token, 2L));

            assertEquals("Invalid user", thrown.getMessage());
        }
    }
}