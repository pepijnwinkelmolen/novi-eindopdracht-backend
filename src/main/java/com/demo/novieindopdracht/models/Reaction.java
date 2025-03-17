package com.demo.novieindopdracht.models;

import jakarta.persistence.*;

@Entity
@Table(name="reactions")
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
