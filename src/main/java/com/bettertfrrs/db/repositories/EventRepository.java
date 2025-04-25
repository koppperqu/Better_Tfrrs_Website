package com.bettertfrrs.db.repositories;

import com.bettertfrrs.db.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event,Integer> {
    Optional<Event> findByShortName(String shortName);
}
