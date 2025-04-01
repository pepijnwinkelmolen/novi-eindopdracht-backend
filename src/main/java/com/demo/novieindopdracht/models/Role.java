package com.demo.novieindopdracht.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="roles")
public class Role {

    @Id
    private String role;
    @ManyToMany(mappedBy = "roles")
    List<User> users;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
