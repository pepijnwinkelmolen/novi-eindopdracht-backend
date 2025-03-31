package com.demo.novieindopdracht.models;

import jakarta.persistence.*;

@Entity
@Table(name="roles")
public class Role {

    @Id
    private Long userId;
    private String role;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
