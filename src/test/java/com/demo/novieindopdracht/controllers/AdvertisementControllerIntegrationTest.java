package com.demo.novieindopdracht.controllers;

import com.demo.novieindopdracht.dtos.AdvertisementOutputDto;
import com.demo.novieindopdracht.dtos.AdvertisementWithImageDto;
import com.demo.novieindopdracht.dtos.BidOutputDto;
import com.demo.novieindopdracht.dtos.CategoryOutputDto;
import com.demo.novieindopdracht.mappers.AdvertisementMapper;
import com.demo.novieindopdracht.services.AdvertisementService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class AdvertisementControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdvertisementService advertisementService;

    @Test
    void shouldReturnFilteredAdvertisements() throws Exception {
        // Arrange
        BidOutputDto bidOutputDto = new BidOutputDto();
        bidOutputDto.username = "Mark";
        bidOutputDto.bidId = 2L;
        bidOutputDto.price = 1200.0;

        CategoryOutputDto categoryOutputDto = new CategoryOutputDto();
        categoryOutputDto.title = "Binnenmeubels";
        categoryOutputDto.categoryId = 34L;
        categoryOutputDto.parentId = 31L;

        AdvertisementOutputDto advertisementOutputDto = new AdvertisementOutputDto();
        advertisementOutputDto.advertisementId = 1L;
        advertisementOutputDto.title = "Bank zo goed als nieuw";
        advertisementOutputDto.description = "Comfortabele bank in goede staat";
        advertisementOutputDto.price = 240.0;
        advertisementOutputDto.image = new ByteArrayResource("bmw.png".getBytes());
        advertisementOutputDto.details = "Donkergrijs";
        advertisementOutputDto.state = "Nieuw";
        advertisementOutputDto.date = LocalDate.now();
        advertisementOutputDto.hasToGo = "checked";
        advertisementOutputDto.userId = 42L;
        advertisementOutputDto.setCategory(List.of(categoryOutputDto));
        advertisementOutputDto.setBids(List.of(bidOutputDto));

        List<AdvertisementOutputDto> outputList = List.of(advertisementOutputDto);
        Mockito.when(advertisementService.getAllAdvertisementsWithFilter(240.0, null, null))
                .thenReturn(outputList);

        AdvertisementWithImageDto dtoWithImage = new AdvertisementWithImageDto();
        dtoWithImage.advertisementId = 1L;
        dtoWithImage.title = "Bank zo goed als nieuw";
        dtoWithImage.description = "Comfortabele bank in goede staat";
        dtoWithImage.price = 240.0;
        dtoWithImage.image = "bmw.png".getBytes();
        dtoWithImage.details = "Donkergrijs";
        dtoWithImage.state = "Nieuw";
        dtoWithImage.date = LocalDate.now();
        dtoWithImage.hasToGo = "checked";
        dtoWithImage.userId = 42L;
        dtoWithImage.category = List.of(categoryOutputDto);
        dtoWithImage.setBids(List.of(bidOutputDto));

        try (MockedStatic<AdvertisementMapper> mockedMapper = Mockito.mockStatic(AdvertisementMapper.class)) {
            mockedMapper.when(() -> AdvertisementMapper.toImageDtoList(outputList))
                    .thenReturn(List.of(dtoWithImage));

            // Act & Assert
            mockMvc.perform(get("/advertisements/filter")
                            .param("price", "240"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].title").value("Bank zo goed als nieuw"));
        }
    }
}