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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidService {

    private final BidRepository bidRepository;
    private final AdvertisementRepository advertisementRepository;
    private final JwtService jwtService;
    private final UserRepository userRepos;

    public BidService(BidRepository bidRepository, AdvertisementRepository advertisementRepository, JwtService jwtService, UserRepository userRepos) {
        this.bidRepository = bidRepository;
        this.advertisementRepository = advertisementRepository;
        this.jwtService = jwtService;
        this.userRepos = userRepos;
    }

    public void createBidOnAdvert(@Valid @NotNull @NotBlank String token, @NotNull @NotBlank Double value, @NotNull @NotBlank Long id) {
        if(ValidateUser.validateUserWithToken(token, jwtService, userRepos)) {
            token = token.replace("Bearer ", "");
            String username = jwtService.extractUsername(token);
            Optional<User> currentUser = userRepos.findByUsername(username);
            if (currentUser.isPresent()) {
                Bid bid = new Bid();
                bid.setPrice(value);
                bid.setUser(currentUser.get());
                Optional<Advertisement> advertisement = advertisementRepository.findByAdvertisementId(id);
                if(advertisement.isPresent()) {
                    bid.setAdvertisement(advertisement.get());
                    List<Bid> bids = advertisement.get().getBids();
                    bids.add(bid);
                    advertisement.get().setBids(bids);
                    bidRepository.save(bid);
                } else {
                    throw new ResourceNotFoundException("No advertisement with ID = " + id);
                }
            } else {
                throw new ResourceNotFoundException("Invalid user");
            }
        } else {
            throw new BadRequestException("Invalid token");
        }
    }
}
