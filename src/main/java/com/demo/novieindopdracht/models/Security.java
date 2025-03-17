package com.demo.novieindopdracht.models;

import jakarta.persistence.*;

@Entity
@Table(name="securities")
public class Security {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
