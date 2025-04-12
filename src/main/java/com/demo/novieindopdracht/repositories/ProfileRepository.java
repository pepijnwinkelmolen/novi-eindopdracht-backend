package com.demo.novieindopdracht.repositories;

import com.demo.novieindopdracht.models.Profile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, String> {
    Profile findByPhoneNumber(String phoneNumber);

    Optional<Profile> findByProfileId(Long profileId);
}
