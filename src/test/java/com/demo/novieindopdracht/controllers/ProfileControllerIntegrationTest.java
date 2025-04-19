package com.demo.novieindopdracht.controllers;

import com.demo.novieindopdracht.dtos.ProfileOutputDto;
import com.demo.novieindopdracht.services.ProfileService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ProfileControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfileService profileService;

    @Test
    void shouldReturnProfile_whenValidTokenAndIdProvided() throws Exception {
        // Arrange
        ProfileOutputDto profileOutputDto = new ProfileOutputDto();
        profileOutputDto.profileId = 1L;
        profileOutputDto.username = "johndoe";
        profileOutputDto.email = "john.doe@example.com";
        profileOutputDto.residence = "Amsterdam";
        profileOutputDto.phoneNumber = "0612345678";

        String token = "Bearer mocktoken";

        Mockito.when(profileService.getProfile(token, 1L)).thenReturn(profileOutputDto);

        // Act & Assert
        mockMvc.perform(get("/profile/1")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profileId").value(1))
                .andExpect(jsonPath("$.username").value("johndoe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.residence").value("Amsterdam"))
                .andExpect(jsonPath("$.phoneNumber").value("0612345678"));
    }
}