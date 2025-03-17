package com.demo.novieindopdracht.models;

import jakarta.persistence.*;

@Entity
@Table(name="advertisements")
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
