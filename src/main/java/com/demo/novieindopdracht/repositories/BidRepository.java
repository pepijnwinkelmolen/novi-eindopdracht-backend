package com.demo.novieindopdracht.repositories;

import com.demo.novieindopdracht.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, String> {
}
