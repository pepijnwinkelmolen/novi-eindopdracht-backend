package com.demo.novieindopdracht.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;
    @OneToOne(mappedBy = "profile")
    User user;
    @NotBlank
    @Size(max = 25)
    private String residence;
    @Column(unique=true)
    @Size(min = 9, max = 15)
    private String phoneNumber;
    @Column(unique=true)
    @Email
    private String email;

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
