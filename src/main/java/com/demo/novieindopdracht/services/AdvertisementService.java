package com.demo.novieindopdracht.services;

import com.demo.novieindopdracht.dtos.AdvertisementInputDto;
import com.demo.novieindopdracht.dtos.AdvertisementOutputDto;
import com.demo.novieindopdracht.dtos.AdvertisementProjectionOutputDto;
import com.demo.novieindopdracht.exceptions.BadRequestException;
import com.demo.novieindopdracht.exceptions.ResourceNotFoundException;
import com.demo.novieindopdracht.mappers.AdvertisementMapper;
import com.demo.novieindopdracht.models.Advertisement;
import com.demo.novieindopdracht.projections.AdvertisementSummary;
import com.demo.novieindopdracht.repositories.AdvertisementRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepos;

    public AdvertisementService(AdvertisementRepository advertisementRepos) {
        this.advertisementRepos = advertisementRepos;
    }

    public List<AdvertisementOutputDto> getAllAdvertisementsLikeQuery(String query) {
        Optional<List<Advertisement>> advertisements = advertisementRepos.findByTitleLike("%" + query + "%");
        if (advertisements.isPresent()) {
            List<Advertisement> advertToDtoList = advertisements.stream()
                    .flatMap(List::stream)
                    .toList();
            if (advertToDtoList.isEmpty()) {
                throw new ResourceNotFoundException("No adverts found with: " + query);
            } else {
                return AdvertisementMapper.toDtoList(advertToDtoList);
            }
        } else {
            throw new ResourceNotFoundException("No adverts found with: " + query);
        }
    }

    public List<AdvertisementProjectionOutputDto> getAllAdvertisements() {
        Optional<List<AdvertisementSummary>> advertisements = advertisementRepos.findAllProjectedBy();
        if (advertisements.isPresent()) {
            List<AdvertisementSummary> advertToDtoList = advertisements.stream()
                    .flatMap(List::stream)
                    .filter(Objects::nonNull)
                    .toList();
            if (advertToDtoList.isEmpty()) {
                throw new ResourceNotFoundException("No adverts found");
            } else {
                return AdvertisementMapper.projectionToDtoList(advertToDtoList);
            }
        } else {
            throw new ResourceNotFoundException("No adverts found");
        }
    }

    public List<AdvertisementOutputDto> getAllAdvertisementsWithFilter(Double price, String since, String hasToGo) {
        Optional<List<Advertisement>> advertisements;
        if (price.isNaN() || price.isInfinite()) {
            throw new BadRequestException("Bad request on price: " + price);
        } else {
            price = price * 10.0;
            if (hasToGo == null) {
                if (Objects.equals(since, "today")) {
                    LocalDate checkDate = LocalDate.now();
                    checkDate.minusDays(1);
                    advertisements = advertisementRepos.findByPriceLessThanAndDateLessThanEqual(price, checkDate);
                } else if (Objects.equals(since, "week")) {
                    LocalDate checkDate = LocalDate.now();
                    checkDate.minusDays(7);
                    advertisements = advertisementRepos.findByPriceLessThanAndDateLessThanEqual(price, checkDate);
                } else if (Objects.equals(since, "month")) {
                    LocalDate checkDate = LocalDate.now();
                    checkDate.minusMonths(1);
                    advertisements = advertisementRepos.findByPriceLessThanAndDateLessThanEqual(price, checkDate);
                } else {
                    advertisements = advertisementRepos.findByPriceLessThan(price);
                }
            } else {
                if (Objects.equals(hasToGo, "checked")) {
                    if (since == null) {
                        advertisements = advertisementRepos.findByPriceLessThanAndHasToGoEquals(price, hasToGo);
                    } else {
                        if (Objects.equals(since, "today")) {
                            LocalDate checkDate = LocalDate.now();
                            checkDate.minusDays(1);
                            advertisements = advertisementRepos.findByPriceLessThanAndHasToGoEqualsAndDateLessThanEqual(price, hasToGo, checkDate);
                        } else if (Objects.equals(since, "week")) {
                            LocalDate checkDate = LocalDate.now();
                            checkDate.minusDays(7);
                            advertisements = advertisementRepos.findByPriceLessThanAndHasToGoEqualsAndDateLessThanEqual(price, hasToGo, checkDate);
                        } else if (Objects.equals(since, "month")) {
                            LocalDate checkDate = LocalDate.now();
                            checkDate.minusMonths(1);
                            advertisements = advertisementRepos.findByPriceLessThanAndHasToGoEqualsAndDateLessThanEqual(price, hasToGo, checkDate);
                        } else {
                            throw new BadRequestException("Bad request on since: " + since);
                        }
                    }
                } else {
                    throw new BadRequestException("Bad request on has to go: " + hasToGo);
                }
            }
        }

        if (advertisements.isPresent()) {
            List<Advertisement> advertToDtoList = advertisements.stream()
                    .flatMap(List::stream)
                    .toList();
            if (advertToDtoList.isEmpty()) {
                throw new ResourceNotFoundException("No adverts found with these parameters");
            } else {
                return AdvertisementMapper.toDtoList(advertToDtoList);
            }
        } else {
            throw new ResourceNotFoundException("No adverts found with these parameters");
        }
    }

    public AdvertisementOutputDto getAdvertisementById(@Valid long id) {
        Optional<Advertisement> advertisement = advertisementRepos.findByAdvertisementId(id);
        if (advertisement.isPresent()) {
            return AdvertisementMapper.toDto(advertisement.get());
        } else {
            throw new ResourceNotFoundException("No adverts found");
        }
    }

    @Transactional
    public void deleteAdvert(@Valid long id) {
        advertisementRepos.deleteByAdvertisementId(id);
    }

    @Transactional
    public AdvertisementOutputDto createAdvert(@Valid AdvertisementInputDto advertisementInputDto) {
        Advertisement item = AdvertisementMapper.toEntity(advertisementInputDto);
        advertisementRepos.save(item);
        return AdvertisementMapper.toDto(item);
    }
}
