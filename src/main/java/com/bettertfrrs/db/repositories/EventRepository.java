package com.bettertfrrs.db.repositories;

import com.bettertfrrs.db.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Integer> {
}
