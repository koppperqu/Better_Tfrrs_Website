package com.bettertfrrs.db.repositories;

import com.bettertfrrs.db.entities.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AthleteRepository extends JpaRepository<Athlete,Integer> {

    List<Athlete> findByTeamId(int teamId);

//    List<Athlete> findByTeamIdAndIsMensTeam(int teamId, Boolean isMensTeam);

    Athlete findByLink(String link);
}
