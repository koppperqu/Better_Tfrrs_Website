package com.bettertfrrs.db.repositories;

import com.bettertfrrs.db.entities.Conference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConferenceRepository extends JpaRepository<Conference,Integer> {
    Conference findByLink(String link);

    Optional<Conference> findByName(String conferenceName);
}
