package com.demo.novieindopdracht.mappers;

import com.demo.novieindopdracht.dtos.AdvertisementInputDto;
import com.demo.novieindopdracht.dtos.AdvertisementOutputDto;
import com.demo.novieindopdracht.dtos.AdvertisementProjectionOutputDto;
import com.demo.novieindopdracht.models.Advertisement;
import com.demo.novieindopdracht.projections.AdvertisementSummary;

import java.util.List;
import java.util.stream.Collectors;

public class AdvertisementMapper {

    public static Advertisement toEntity(AdvertisementInputDto advertisementInputDto, String filename) {
        Advertisement advertisement = new Advertisement();
        advertisement.setTitle(advertisementInputDto.title);
        advertisement.setDescription(advertisementInputDto.description);
        advertisement.setPrice(advertisementInputDto.price);
        advertisement.setImage(filename);
        advertisement.setDetails(advertisementInputDto.details);
        advertisement.setState(advertisementInputDto.state);

        return advertisement;
    }

    public static AdvertisementOutputDto toDto(Advertisement advertisement) {
        AdvertisementOutputDto advertisementOutputDto = new AdvertisementOutputDto();
        advertisementOutputDto.advertisementId = advertisement.getAdvertisementId();
        advertisementOutputDto.title = advertisement.getTitle();
        advertisementOutputDto.description = advertisement.getDescription();
        advertisementOutputDto.price = advertisement.getPrice();
        advertisementOutputDto.image = advertisement.getImage();
        advertisementOutputDto.details = advertisement.getDetails();
        advertisementOutputDto.state = advertisement.getState();
        advertisementOutputDto.date = advertisement.getDate();
        advertisementOutputDto.hasToGo = advertisement.getHasToGo();

        return advertisementOutputDto;
    }

    public static AdvertisementProjectionOutputDto projectionToDto(AdvertisementSummary advertisement) {
        AdvertisementProjectionOutputDto advertisementProjectionOutputDto = new AdvertisementProjectionOutputDto();
        advertisementProjectionOutputDto.advertisementId = advertisement.getAdvertisementId();
        advertisementProjectionOutputDto.title = advertisement.getTitle();
        advertisementProjectionOutputDto.price = advertisement.getPrice();
        advertisementProjectionOutputDto.image = advertisement.getImage();

        return advertisementProjectionOutputDto;
    }

    public static List<AdvertisementOutputDto> toDtoList(List<Advertisement> advertisements) {
        return advertisements.stream().map(AdvertisementMapper::toDto).collect(Collectors.toList());
    }

    public static List<AdvertisementProjectionOutputDto> projectionToDtoList(List<AdvertisementSummary> advertisements) {
        return advertisements.stream().map(AdvertisementMapper::projectionToDto).collect(Collectors.toList());
    }

}
