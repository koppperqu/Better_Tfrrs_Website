package com.bettertfrrs.db.repositories;

import com.bettertfrrs.db.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Integer> {
    Team findByLink(String link);

    Optional<List<Team>> findByConferenceId(Integer conferenceId);

    Optional<Team> findByName(String teamName);
}
