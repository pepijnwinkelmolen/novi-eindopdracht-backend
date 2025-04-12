package com.demo.novieindopdracht.services;

import com.demo.novieindopdracht.exceptions.BadRequestException;
import com.demo.novieindopdracht.exceptions.ResourceNotFoundException;
import com.demo.novieindopdracht.helpers.ValidateUser;
import com.demo.novieindopdracht.models.Advertisement;
import com.demo.novieindopdracht.models.Bid;
import com.demo.novieindopdracht.models.User;
import com.demo.novieindopdracht.repositories.AdvertisementRepository;
import com.demo.novieindopdracht.repositories.BidRepository;
import com.demo.novieindopdracht.repositories.UserRepository;
import com.demo.novieindopdracht.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BidServiceTest {

    @Mock
    JwtService jwtService;

    @Mock
    UserRepository userRepository;

    @Mock
    BidRepository bidRepository;

    @Mock
    AdvertisementRepository advertisementRepository;

    @InjectMocks
    BidService bidService;

    /* METHOD: createBidOnAdvert */

    @Test
    void userSuccessfullyCreatesBidOnAdvert() {
        // Arrange
        String token = "Bearer validToken";
        String cleanToken = "validToken";
        double bidValue = 50.0;
        long advertId = 1L;

        User user = new User();
        user.setUsername("user123");

        Advertisement advert = new Advertisement();
        advert.setAdvertisementId(advertId);
        advert.setBids(new ArrayList<>());

        try (MockedStatic<ValidateUser> mockStatic = Mockito.mockStatic(ValidateUser.class)) {
            mockStatic
                    .when(() -> ValidateUser.validateUserWithToken(token, jwtService, userRepository))
                    .thenReturn(true);

            when(jwtService.extractUsername(cleanToken)).thenReturn("user123");
            when(userRepository.findByUsername("user123")).thenReturn(Optional.of(user));
            when(advertisementRepository.findByAdvertisementId(advertId)).thenReturn(Optional.of(advert));
            when(bidRepository.save(Mockito.<Bid>any())).thenAnswer(inv -> inv.getArgument(0));

            // Act
            bidService.createBidOnAdvert(token, bidValue, advertId);

            // Assert
            assertEquals(1, advert.getBids().size());
            Bid createdBid = advert.getBids().get(0);
            assertEquals(bidValue, createdBid.getPrice());
            assertEquals(user, createdBid.getUser());
            assertEquals(advert, createdBid.getAdvertisement());

            verify(bidRepository).save(createdBid);
        }
    }

    @Test
    void userInputWithInvalidToken() {
        // Arrange
        String token = "Bearer fakeToken";

        try (MockedStatic<ValidateUser> mockStatic = Mockito.mockStatic(ValidateUser.class)) {
            mockStatic
                    .when(() -> ValidateUser.validateUserWithToken(token, jwtService, userRepository))
                    .thenReturn(false);

            // Act & Assert
            BadRequestException thrown = assertThrows(BadRequestException.class, () ->
                    bidService.createBidOnAdvert(token, 100.0, 1L));

            assertEquals("Invalid token", thrown.getMessage());
        }
    }

    @Test
    void userNotFound() {
        // Arrange
        String token = "Bearer validToken";
        String cleanToken = "validToken";

        try (MockedStatic<ValidateUser> mockStatic = Mockito.mockStatic(ValidateUser.class)) {
            mockStatic
                    .when(() -> ValidateUser.validateUserWithToken(token, jwtService, userRepository))
                    .thenReturn(true);

            when(jwtService.extractUsername(cleanToken)).thenReturn("ghostUser");
            when(userRepository.findByUsername("ghostUser")).thenReturn(Optional.empty());

            // Act & Assert
            ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                    bidService.createBidOnAdvert(token, 100.0, 1L));

            assertEquals("Invalid user", thrown.getMessage());
            verify(userRepository).findByUsername("ghostUser");
        }
    }

    @Test
    void advertisementNotFound() {
        // Arrange
        String rawToken = "Bearer validToken";
        String cleanToken = "validToken";
        long advertId = 123L;

        User user = new User();
        user.setUsername("user123");

        try (MockedStatic<ValidateUser> mockStatic = Mockito.mockStatic(ValidateUser.class)) {
            mockStatic
                    .when(() -> ValidateUser.validateUserWithToken(rawToken, jwtService, userRepository))
                    .thenReturn(true);

            when(jwtService.extractUsername(cleanToken)).thenReturn("user123");
            when(userRepository.findByUsername("user123")).thenReturn(Optional.of(user));
            when(advertisementRepository.findByAdvertisementId(advertId)).thenReturn(Optional.empty());

            // Act & Assert
            ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                    bidService.createBidOnAdvert(rawToken, 100.0, advertId));

            assertEquals("No advertisement with ID = " + advertId, thrown.getMessage());
            verify(advertisementRepository).findByAdvertisementId(advertId);
        }
    }
}