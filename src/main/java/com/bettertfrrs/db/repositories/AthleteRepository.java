package com.bettertfrrs.db.repositories;

import com.bettertfrrs.db.entities.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AthleteRepository extends JpaRepository<Athlete,Integer> {

    List<Athlete> findByTeamID(int teamID);

    List<Athlete> findByTeamIDAndIsMensTeam(int teamID, Boolean isMensTeam);
}
