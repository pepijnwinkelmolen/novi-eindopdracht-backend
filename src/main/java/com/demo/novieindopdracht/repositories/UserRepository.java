package com.demo.novieindopdracht.repositories;

import com.demo.novieindopdracht.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
