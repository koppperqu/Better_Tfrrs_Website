package com.bettertfrrs.db.repositories;

import com.bettertfrrs.db.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,Integer> {
}
