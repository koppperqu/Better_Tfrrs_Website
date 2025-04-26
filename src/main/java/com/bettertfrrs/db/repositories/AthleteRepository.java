package com.bettertfrrs.db.repositories;

import com.bettertfrrs.db.entities.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AthleteRepository extends JpaRepository<Athlete,Integer> {

    Optional<List<Athlete>> findByTeamId(int teamId);

//    List<Athlete> findByTeamIdAndIsMensTeam(int teamId, Boolean isMensTeam);

    Athlete findByLink(String link);

    Optional<List<Athlete>> findByTeamIdAndIsMan(int id, boolean b);

    Optional<Athlete> findByNameAndTeamId(String athleteName, int id);
}
