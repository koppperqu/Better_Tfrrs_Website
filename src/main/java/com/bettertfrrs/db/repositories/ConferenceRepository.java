package com.bettertfrrs.db.repositories;

import com.bettertfrrs.db.entities.Conference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceRepository extends JpaRepository<Conference,Integer> {
    Conference findByLink(String link);
}
