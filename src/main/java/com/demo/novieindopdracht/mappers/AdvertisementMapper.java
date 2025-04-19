package com.demo.novieindopdracht.mappers;

import com.demo.novieindopdracht.dtos.*;
import com.demo.novieindopdracht.models.Advertisement;
import com.demo.novieindopdracht.projections.AdvertisementSummary;
import com.demo.novieindopdracht.services.StorageService;

import java.io.IOException;
import java.nio.file.Files;
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
        advertisement.setHasToGo(advertisementInputDto.hasToGo);

        return advertisement;
    }

    public static AdvertisementOutputDto toDto(Advertisement advertisement) {
        AdvertisementOutputDto advertisementOutputDto = new AdvertisementOutputDto();
        advertisementOutputDto.advertisementId = advertisement.getAdvertisementId();
        advertisementOutputDto.setCategory(CategoryMapper.toDtoList(advertisement.getCategories()));
        advertisementOutputDto.title = advertisement.getTitle();
        advertisementOutputDto.description = advertisement.getDescription();
        advertisementOutputDto.price = advertisement.getPrice();
        advertisementOutputDto.details = advertisement.getDetails();
        advertisementOutputDto.state = advertisement.getState();
        advertisementOutputDto.date = advertisement.getDate();
        advertisementOutputDto.hasToGo = advertisement.getHasToGo();
        advertisementOutputDto.userId = advertisement.getUser().getUserId();
        advertisementOutputDto.setBids(BidMapper.toDtoList(advertisement.getBids()));

        return advertisementOutputDto;
    }

    public static AdvertisementWithImageDto toImageDto(AdvertisementOutputDto advertisementOutputDto) throws IOException {
        AdvertisementWithImageDto advertisementWithImageDto = new AdvertisementWithImageDto();
        advertisementWithImageDto.advertisementId = advertisementOutputDto.advertisementId;
        advertisementWithImageDto.category = advertisementOutputDto.category;
        advertisementWithImageDto.title = advertisementOutputDto.title;
        advertisementWithImageDto.description = advertisementOutputDto.description;
        advertisementWithImageDto.price = advertisementOutputDto.price;
        advertisementWithImageDto.image = Files.readAllBytes(advertisementOutputDto.image.getFile().toPath());
        advertisementWithImageDto.details = advertisementOutputDto.details;
        advertisementWithImageDto.state = advertisementOutputDto.state;
        advertisementWithImageDto.date = advertisementOutputDto.date;
        advertisementWithImageDto.hasToGo = advertisementOutputDto.hasToGo;
        advertisementWithImageDto.userId = advertisementOutputDto.userId;
        advertisementWithImageDto.setBids(advertisementOutputDto.getBids());

        return advertisementWithImageDto;
    }

    public static AdvertisementProjectionWithImageDto toProjectionWithImageDto(AdvertisementProjectionOutputDto advertisementProjectionOutputDto) throws IOException {
        AdvertisementProjectionWithImageDto advertisementProjectionWithImageDto = new AdvertisementProjectionWithImageDto();
        advertisementProjectionWithImageDto.advertisementId = advertisementProjectionOutputDto.advertisementId;
        advertisementProjectionWithImageDto.title = advertisementProjectionOutputDto.title;
        advertisementProjectionWithImageDto.price = advertisementProjectionOutputDto.price;
        advertisementProjectionWithImageDto.image = Files.readAllBytes(advertisementProjectionOutputDto.image.getFile().toPath());

        return advertisementProjectionWithImageDto;
    }

    public static AdvertisementProjectionOutputDto projectionToDto(AdvertisementSummary advertisement) throws IOException {
        AdvertisementProjectionOutputDto advertisementProjectionOutputDto = new AdvertisementProjectionOutputDto();
        advertisementProjectionOutputDto.advertisementId = advertisement.getAdvertisementId();
        advertisementProjectionOutputDto.title = advertisement.getTitle();
        advertisementProjectionOutputDto.price = advertisement.getPrice();

        return advertisementProjectionOutputDto;
    }

    public static List<AdvertisementOutputDto> toDtoList(List<Advertisement> advertisements, StorageService storageService) {
        return advertisements.stream().map((advertisement -> {
            AdvertisementOutputDto dto = AdvertisementMapper.toDto(advertisement);
            dto.setImage(storageService.loadAsResource(advertisement.getImage()));
            return dto;
        })).collect(Collectors.toList());
    }

    public static List<AdvertisementProjectionOutputDto> projectionToDtoList(List<AdvertisementSummary> advertisements, StorageService storageService) throws IOException {
        return advertisements.stream().map((advertisement -> {
            try{
                AdvertisementProjectionOutputDto advertisementProjectionOutputDto = AdvertisementMapper.projectionToDto(advertisement);
                advertisementProjectionOutputDto.setImage(storageService.loadAsResource(advertisement.getImage()));
                return advertisementProjectionOutputDto;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        })).collect(Collectors.toList());
    }

    public static List<AdvertisementWithImageDto> toImageDtoList(List<AdvertisementOutputDto> advertisementOutputDtoList) throws IOException {
        return advertisementOutputDtoList.stream().map((advertisementOutputDto -> {
            try{
                return AdvertisementMapper.toImageDto(advertisementOutputDto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        })).collect(Collectors.toList());
    }

    public static List<AdvertisementProjectionWithImageDto> toProjectionImageDtoList(List<AdvertisementProjectionOutputDto> advertisementProjectionOutputDtoList) {
        return advertisementProjectionOutputDtoList.stream().map((advertisementProjectionOutputDto -> {
            try{
                return AdvertisementMapper.toProjectionWithImageDto(advertisementProjectionOutputDto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        })).collect(Collectors.toList());
    }
}
