package com.demo.novieindopdracht.services;

import com.demo.novieindopdracht.dtos.NewUserDto;
import com.demo.novieindopdracht.dtos.UserOutputDto;
import com.demo.novieindopdracht.exceptions.BadRequestException;
import com.demo.novieindopdracht.helpers.ValidateUser;
import com.demo.novieindopdracht.models.Profile;
import com.demo.novieindopdracht.models.Role;
import com.demo.novieindopdracht.models.User;
import com.demo.novieindopdracht.repositories.ProfileRepository;
import com.demo.novieindopdracht.repositories.RoleRepository;
import com.demo.novieindopdracht.repositories.UserRepository;
import com.demo.novieindopdracht.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    JwtService jwtService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    ProfileRepository profileRepository;

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    UserService userService;

    /* METHOD: loginUser */

    @Test
    void shouldReturnUserOutputDtoAfterTokenCheck() {
        // Arrange
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";
        User user = new User();
        Profile profile = new Profile();
        List<String> fakeRoles = new ArrayList<>();
        fakeRoles.add("ROLE_USER");
        Role role = new Role();
        role.setRole("ROLE_USER");
        user.setUsername("Barry123");
        user.setPassword("12345678");
        user.setUserId(1234L);
        user.setProfile(profile);
        user.setRoles(List.of(role));
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(jwtService.extractUsername(token.replace("Bearer ", ""))).thenReturn("Barry123");
        when(jwtService.isTokenExpired(token.replace("Bearer ", ""))).thenReturn(false);

        // Act
        UserOutputDto result = userService.loginUser(token);

        // Assert
        assertEquals(1234L, result.userId);
        assertEquals("Barry123", result.username);
        assertEquals(fakeRoles, result.roles);
    }

    /* METHOD: createUser */

    @Test
    void shouldCreateNewUserWithUserRole() {
        // Arrange
        NewUserDto newUserDto = new NewUserDto();
        Profile profile = new Profile();
        Role roleUser = new Role();
        User user = new User();

        newUserDto.username = "Henk539";
        newUserDto.password = "123456213";
        newUserDto.residence = "woonplaats";
        newUserDto.phoneNumber = "1231231231";
        newUserDto.email = "leukeemail@mail.com";

        profile.setProfileId(1234L);
        profile.setEmail(newUserDto.email);
        profile.setResidence(newUserDto.residence);
        profile.setPhoneNumber(newUserDto.phoneNumber);

        roleUser.setRole("ROLE_USER");

        user.setUserId(1234L);
        user.setUsername(newUserDto.username);
        user.setPassword(newUserDto.password);

        when(passwordEncoder.encode(anyString())).thenReturn("wachtwoord");
        when(profileRepository.save(Mockito.<Profile>any())).thenReturn(profile);
        when(profileRepository.findByPhoneNumber(anyString())).thenReturn(profile);
        when(roleRepository.findById("ROLE_USER")).thenReturn(Optional.of(roleUser));
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);

        // Act
        userService.createUser(newUserDto);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals("123456213", user.getPassword());
        assertEquals(profile, savedUser.getProfile());
        assertTrue(savedUser.getRoles().contains(roleUser));
    }

    /* METHOD: setNewPassword */

    @Test
    void shouldSetNewPassword() {
        // Arrange
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";
        String cleanToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";
        String password = "123456213";
        String encodedPassword = "Encoded123456213";

        User user = new User();
        user.setUsername("Sebbie107");

        when(jwtService.extractUsername(cleanToken)).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.save(Mockito.<User>any())).thenAnswer(i -> i.getArguments()[0]);

        // Act
        userService.setNewPassword(token, password);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals(encodedPassword, savedUser.getPassword());
    }

    /* METHOD: setNewUsername */

    @Test
    void shouldSetNewUsername() {
        // Arrange
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";
        String cleanToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";
        String newUsername = "Bep69203";

        User currentUser = new User();
        currentUser.setUsername("OldUsername");

        when(userRepository.findByUsername(newUsername)).thenReturn(Optional.empty());
        when(jwtService.extractUsername(cleanToken)).thenReturn("OldUsername");
        when(userRepository.findByUsername("OldUsername")).thenReturn(Optional.of(currentUser));
        when(userRepository.save(Mockito.<User>any())).thenAnswer(inv -> inv.getArgument(0));

        // Act
        userService.setNewUsername(token, newUsername);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals(newUsername, savedUser.getUsername());
    }

    /* METHOD: deleteUserById */

    @Test
    void shouldDeleteUserByIdUsingAdminAccount() {
        // Arrange
        String rawToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";
        String cleanToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";
        long targetUserId = 1234L;

        User adminUser = new User();
        adminUser.setUsername("adminUser");
        Role adminRole = new Role();
        adminRole.setRole("ROLE_ADMIN");
        adminUser.setRoles(List.of(adminRole));

        User targetUser = new User();
        targetUser.setUserId(targetUserId);
        targetUser.setUsername("someUser");

        when(jwtService.extractUsername(cleanToken)).thenReturn("adminUser");
        when(userRepository.findByUsername("adminUser")).thenReturn(Optional.of(adminUser));
        when(userRepository.findByUserId(targetUserId)).thenReturn(Optional.of(targetUser));

        // Act
        userService.deleteUserById(rawToken, targetUserId);

        // Assert
        verify(userRepository).deleteByUserId(targetUserId);
        verify(userRepository, atLeastOnce()).findByUsername("adminUser");
        verify(userRepository).findByUserId(targetUserId);
    }

    @Test
    void userDeletesSelfWithRoleUser() {
        // Arrange
        String token = "Bearer selftoken123";
        String cleanToken = "selftoken123";
        long selfUserId = 42L;

        User currentUser = new User();
        currentUser.setUsername("selfUser");
        Role userRole = new Role();
        userRole.setRole("ROLE_USER"); // geen admin
        currentUser.setRoles(List.of(userRole));
        currentUser.setUserId(selfUserId); // dezelfde als target

        User targetUser = new User();
        targetUser.setUserId(selfUserId);
        targetUser.setUsername("selfUser");

        try (MockedStatic<ValidateUser> validateUserMock = Mockito.mockStatic(ValidateUser.class)) {
            validateUserMock
                    .when(() -> ValidateUser.validateUserWithToken(token, jwtService, userRepository))
                    .thenReturn(true);

            when(jwtService.extractUsername(cleanToken)).thenReturn("selfUser");
            when(userRepository.findByUsername("selfUser")).thenReturn(Optional.of(currentUser));
            when(userRepository.findByUserId(selfUserId)).thenReturn(Optional.of(targetUser));

            // Act
            userService.deleteUserById(token, selfUserId);

            // Assert
            verify(userRepository).deleteByUserId(selfUserId);
            validateUserMock.verify(() -> ValidateUser.validateUserWithToken(token, jwtService, userRepository));
        }
    }

    @Test
    void userTriesToDeleteOtherUser() {
        // Arrange
        String rawToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";
        String cleanToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaXBqZSIsImlhdCI6MTc0NDMwOTM5MywiZXhwIjoxNzQ1MTczMzkzfQ.qxu4HzuwBm0vSBJ4hHnSvvAnOmAm3h1UuzYMW3ERWeU";
        long targetUserId = 42L;

        // De ingelogde gebruiker (geen admin)
        User currentUser = new User();
        currentUser.setUsername("currentUser");
        Role userRole = new Role();
        userRole.setRole("ROLE_USER");
        currentUser.setRoles(List.of(userRole));

        // Doelwit van de delete (andere gebruiker)
        User targetUser = new User();
        targetUser.setUserId(targetUserId);
        targetUser.setUsername("someoneElse");


        when(jwtService.extractUsername(cleanToken)).thenReturn("currentUser");
        when(userRepository.findByUsername("currentUser")).thenReturn(Optional.of(currentUser));
        when(userRepository.findByUserId(targetUserId)).thenReturn(Optional.of(targetUser));

        // Act & Assert
        BadRequestException thrown = assertThrows(BadRequestException.class, () ->
                userService.deleteUserById(rawToken, targetUserId));

        assertEquals("Invalid user", thrown.getMessage());
        verify(userRepository, never()).deleteByUserId(anyLong());
    }

    @Test
    void userTriesToDeleteButUsesInvalidToken() {
        // Arrange
        String rawToken = "Bearer invalidToken";
        long userId = 1L;

        try (MockedStatic<ValidateUser> validateUserMock = Mockito.mockStatic(ValidateUser.class)) {
            validateUserMock
                    .when(() -> ValidateUser.validateUserWithToken(rawToken, jwtService, userRepository))
                    .thenReturn(false);
            // Act & Assert
            BadRequestException thrown = assertThrows(BadRequestException.class, () ->
                    userService.deleteUserById(rawToken, userId));

            assertEquals("User unauthorized", thrown.getMessage());
            verify(userRepository, never()).deleteByUserId(anyLong());
        }
    }

    @Test
    void loggedInUserNotFound() {
        // Arrange
        String rawToken = "Bearer validToken";
        String cleanToken = "validToken";
        long userId = 1L;

        when(jwtService.extractUsername(cleanToken)).thenReturn("emptyUser");
        when(userRepository.findByUsername("emptyUser")).thenReturn(Optional.empty());

        // Act & Assert
        BadRequestException thrown = assertThrows(BadRequestException.class, () ->
                userService.deleteUserById(rawToken, userId));

        assertEquals("Invalid token", thrown.getMessage());
        verify(userRepository, never()).deleteByUserId(anyLong());
    }

    @Test
    void targetUserNotFound() {
        // Arrange
        String token = "Bearer validToken";
        String cleanToken = "validToken";
        long targetUserId = 999L;

        User currentUser = new User();
        currentUser.setUsername("user123");
        Role userRole = new Role();
        userRole.setRole("ROLE_USER");
        currentUser.setRoles(List.of(userRole));

        try (MockedStatic<ValidateUser> validateUserMock = Mockito.mockStatic(ValidateUser.class)) {
            validateUserMock
                    .when(() -> ValidateUser.validateUserWithToken(token, jwtService, userRepository))
                    .thenReturn(true);

            when(jwtService.extractUsername(cleanToken)).thenReturn("user123");
            when(userRepository.findByUsername("user123")).thenReturn(Optional.of(currentUser));
            when(userRepository.findByUserId(targetUserId)).thenReturn(Optional.empty());

            // Act & Assert
            BadRequestException thrown = assertThrows(BadRequestException.class, () ->
                    userService.deleteUserById(token, targetUserId));

            assertEquals("Invalid input", thrown.getMessage());
            verify(userRepository, never()).deleteByUserId(anyLong());
        }
    }

    @Test
    void tokenUserAndIdUserMismatch() {
        // Arrange
        String token = "Bearer validToken";
        String cleanToken = "validToken";
        long targetUserId = 42L;

        User currentUser = new User();
        currentUser.setUsername("actualUser");
        Role userRole = new Role();
        userRole.setRole("ROLE_USER");
        currentUser.setRoles(List.of(userRole));

        User targetUser = new User();
        targetUser.setUserId(targetUserId);
        targetUser.setUsername("someoneElse");

        try (MockedStatic<ValidateUser> validateUserMock = Mockito.mockStatic(ValidateUser.class)) {
            validateUserMock
                    .when(() -> ValidateUser.validateUserWithToken(token, jwtService, userRepository))
                    .thenReturn(true);

            when(jwtService.extractUsername(cleanToken)).thenReturn("actualUser");
            when(userRepository.findByUsername("actualUser")).thenReturn(Optional.of(currentUser));
            when(userRepository.findByUserId(targetUserId)).thenReturn(Optional.of(targetUser));

            // Act & Assert
            BadRequestException thrown = assertThrows(BadRequestException.class, () ->
                    userService.deleteUserById(token, targetUserId));

            assertEquals("Invalid user", thrown.getMessage());
            verify(userRepository, never()).deleteByUserId(anyLong());
        }
    }
}