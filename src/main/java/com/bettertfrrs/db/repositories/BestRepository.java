package com.bettertfrrs.db.repositories;

import com.bettertfrrs.db.entities.Best;
import com.bettertfrrs.db.entities.BestId;
import com.bettertfrrs.db.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BestRepository extends JpaRepository<Best, BestId> {

    List<Best> findByAthleteId(Integer athleteId);

    Best findByAthleteIdAndEventId(Integer athleteId, Integer eventId);

    @Query("SELECT DISTINCT b.event FROM Best b WHERE b.athlete.team.id = :TEAMID AND b.athlete.isMan = :ISMENSTEAM")
    Optional<List<Event>> getDistinctEventsForTeamAndIsMensTeam(@Param("TEAMID") Integer id,@Param("ISMENSTEAM") Boolean isMensTeam);

    @Query("SELECT DISTINCT b.event FROM Best b WHERE b.athlete.team.id = :TEAMID")
    Optional<List<Event>> getDistinctEventsForTeam(@Param("TEAMID")int id);

    Optional<List<Best>> findByEventIdAndAthleteTeamId(int eventId, int teamId);

    Optional<List<Best>> findByEventIdAndAthleteTeamIdAndAthleteIsMan(int eventId, int teamId, boolean isMensTeam);
}
