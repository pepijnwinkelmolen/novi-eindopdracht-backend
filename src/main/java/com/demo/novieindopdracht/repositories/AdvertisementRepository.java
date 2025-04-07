package com.demo.novieindopdracht.repositories;

import com.demo.novieindopdracht.models.Advertisement;
import com.demo.novieindopdracht.projections.AdvertisementSummary;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, String> {
    Optional<List<AdvertisementSummary>> findAllProjectedBy();

    Optional<List<Advertisement>> findByTitleLike(String input);

    Optional<List<Advertisement>> findByPriceLessThan(Double price);

    Optional<List<Advertisement>> findByPriceLessThanAndHasToGoEquals(Double price, String hasToGo);

    Optional<List<Advertisement>> findByPriceLessThanAndHasToGoEqualsAndDateLessThanEqual(Double price, String hasToGo, LocalDate checkDate);

    Optional<List<Advertisement>> findByPriceLessThanAndDateLessThanEqual(Double price, LocalDate checkDate);

    Optional<Advertisement> findByAdvertisementId(@Valid long id);

    @Transactional
    void deleteByAdvertisementId(@Valid long id);

    Optional<List<Advertisement>> getAdvertisementsByCategoryId(Long categoryId);
}
