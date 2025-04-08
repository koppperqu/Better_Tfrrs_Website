package com.bettertfrrs.db.repositories;

import com.bettertfrrs.db.entities.Best;
import com.bettertfrrs.db.entities.BestID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BestRepository extends JpaRepository<Best, BestID> {
    List<Best> findByAthleteID(int athleteID);
}
