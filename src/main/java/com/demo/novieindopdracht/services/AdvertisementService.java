package com.demo.novieindopdracht.services;

import com.demo.novieindopdracht.dtos.AdvertisementInputDto;
import com.demo.novieindopdracht.dtos.AdvertisementOutputDto;
import com.demo.novieindopdracht.dtos.AdvertisementProjectionOutputDto;
import com.demo.novieindopdracht.exceptions.BadRequestException;
import com.demo.novieindopdracht.exceptions.ResourceNotFoundException;
import com.demo.novieindopdracht.helpers.validateUser;
import com.demo.novieindopdracht.mappers.AdvertisementMapper;
import com.demo.novieindopdracht.models.Advertisement;
import com.demo.novieindopdracht.models.Category;
import com.demo.novieindopdracht.models.Role;
import com.demo.novieindopdracht.models.User;
import com.demo.novieindopdracht.projections.AdvertisementSummary;
import com.demo.novieindopdracht.repositories.AdvertisementRepository;
import com.demo.novieindopdracht.repositories.CategoryRepository;
import com.demo.novieindopdracht.repositories.UserRepository;
import com.demo.novieindopdracht.security.JwtService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepos;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepos;
    private final JwtService jwtService;
    private final StorageService storageService;

    public AdvertisementService(AdvertisementRepository advertisementRepos, CategoryRepository categoryRepository, UserRepository userRepos, JwtService jwtService, StorageService storageService) {
        this.advertisementRepos = advertisementRepos;
        this.categoryRepository = categoryRepository;
        this.userRepos = userRepos;
        this.jwtService = jwtService;
        this.storageService = storageService;
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
                return AdvertisementMapper.toDtoList(advertToDtoList, storageService);
            }
        } else {
            throw new ResourceNotFoundException("No adverts found with: " + query);
        }
    }

    public List<AdvertisementProjectionOutputDto> getAllAdvertisements() throws IOException {
        Optional<List<AdvertisementSummary>> advertisements = advertisementRepos.findAllProjectedBy();
        if (advertisements.isPresent()) {
            List<AdvertisementSummary> advertToDtoList = advertisements.stream()
                    .flatMap(List::stream)
                    .filter(Objects::nonNull)
                    .toList();
            if (advertToDtoList.isEmpty()) {
                throw new ResourceNotFoundException("No adverts found");
            } else {
                return AdvertisementMapper.projectionToDtoList(advertToDtoList, storageService);
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
                return AdvertisementMapper.toDtoList(advertToDtoList, storageService);
            }
        } else {
            throw new ResourceNotFoundException("No adverts found with these parameters");
        }
    }

    public List<AdvertisementOutputDto> getAllAdvertisementsByCategory(@Valid String category) {
        Optional<Category> cat = categoryRepository.findByTitle(category);
        if(cat.isPresent()) {
            List<Category> catList = new ArrayList<>();
            catList.add(cat.get());
            Optional<List<Advertisement>> items = advertisementRepos.getAdvertisementsByCategories(catList);
            if (items.isPresent()) {
                return AdvertisementMapper.toDtoList(items.get(), storageService);
            } else {
                throw new ResourceNotFoundException("No adverts found");
            }
        } else {
            throw new ResourceNotFoundException("No such category");
        }
    }

    public AdvertisementOutputDto getAdvertisementById(@Valid long id) {
        Optional<Advertisement> advertisement = advertisementRepos.findByAdvertisementId(id);
        if (advertisement.isPresent()) {
            AdvertisementOutputDto advertisementOutputDto = AdvertisementMapper.toDto(advertisement.get());
            advertisementOutputDto.setImage(storageService.loadAsResource(advertisement.get().getImage()));
            return advertisementOutputDto;
        } else {
            throw new ResourceNotFoundException("No adverts found");
        }
    }

    @Transactional
    public void deleteAdvert(@Valid @NotNull @NotBlank String token, @Valid long id) {
        if(validateUser.validateUserWithToken(token, jwtService, userRepos)) {
            token = token.replace("Bearer ", "");
            String username = jwtService.extractUsername(token);
            Optional<User> currentUser = userRepos.findByUsername(username);
            if(currentUser.isPresent()) {
                List<Role> roles = currentUser.get().getRoles();
                String myRole = "user";
                for (Role value : roles) {
                    String role = value.getRole();
                    if (Objects.equals(role, "ROLE_ADMIN")) {
                        myRole = "admin";
                        break;
                    }
                }
                if(Objects.equals(myRole, "admin")) {
                    advertisementRepos.deleteByAdvertisementId(id);
                } else {
                    Optional<Advertisement> optionalAdvertisement = advertisementRepos.findByAdvertisementId(id);
                    if(optionalAdvertisement.isPresent()) {
                        if(Objects.equals(optionalAdvertisement.get().getUser().getUsername(), username)) {
                            advertisementRepos.deleteByAdvertisementId(id);
                        } else {
                            throw new BadRequestException("Invalid user");
                        }
                    } else {
                        throw new BadRequestException("Invalid input");
                    }
                }
            } else {
                throw new BadRequestException("Invalid input");
            }
        } else {
            throw new BadRequestException("User unauthorized");
        }
    }

    @Transactional
    public Long createAdvert(@Valid @NotNull @NotBlank String token, @Valid AdvertisementInputDto advertisementInputDto) {
        try {
            if(validateUser.validateUserWithToken(token, jwtService, userRepos)) {
                token = token.replace("Bearer ", "");
                String username = jwtService.extractUsername(token);
                Optional<User> currentUser = userRepos.findByUsername(username);
                if(currentUser.isPresent()) {
                    String filename = storageService.store(advertisementInputDto.image);
                    Advertisement item = AdvertisementMapper.toEntity(advertisementInputDto, filename);
                    item.setUser(currentUser.get());
                    item.setImage(filename);
                    Optional<Category> cat = categoryRepository.findByTitle(advertisementInputDto.category);
                    if(cat.isPresent()) {
                        List<Category> catList = new ArrayList<>();
                        catList.add(cat.get());
                        item.setCategories(catList);
                        advertisementRepos.save(item);
                        return item.getAdvertisementId();
                    } else {
                        throw new BadRequestException("Invalid category");
                    }
                } else {
                    throw new BadRequestException("Invalid token");
                }
            } else {
                throw new BadRequestException("Invalid token");
            }
        } catch (BadRequestException err) {
            throw new BadRequestException(err.getMessage());
        }
    }
}
