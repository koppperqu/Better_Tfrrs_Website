package com.bettertfrrs.db.repositories;

import com.bettertfrrs.db.models.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AthleteRepository extends JpaRepository<Athlete,Integer> {
}
