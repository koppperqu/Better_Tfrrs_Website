package com.bettertfrrs.db.services;

import com.bettertfrrs.db.entities.Best;
import com.bettertfrrs.db.entities.Event;
import com.bettertfrrs.db.repositories.BestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BestService {
    private final BestRepository bestRepository;

    public BestService(BestRepository bestRepository){
        this.bestRepository = bestRepository;
    }

    public Optional<List<Best>> getBestsByAthleteId(int athleteId) {
        return bestRepository.findByAthleteId(athleteId);
    }

    public Best createOrUpdateBest(Best best) {
        Best bestInDB = getBestByAthleteIdAndEventId(best.athlete.id,best.event.id);
        if (bestInDB != null) {
            bestInDB.setMark(best.mark);
            bestInDB.setLink(best.link);
            return bestRepository.save(bestInDB);
        }else {
            return bestRepository.save(best);
        }
    }

    private Best getBestByAthleteIdAndEventId(Integer athleteId, Integer eventId) {
        return bestRepository.findByAthleteIdAndEventId(athleteId, eventId);
    }

    public Optional<List<Event>> getDistinctEventsForTeamAndIsMensTeam(int id, Boolean isMensTeam) {
        return bestRepository.getDistinctEventsForTeamAndIsMensTeam(id, isMensTeam);
    }

    public Optional<List<Event>> getDistinctEventsForTeam(int id) {
        return bestRepository.getDistinctEventsForTeam(id);
    }

    public Optional<List<Best>> getBestsForEventAndForTeam(int eventId, int teamId) {
        return bestRepository.findByEventIdAndAthleteTeamId(eventId,teamId);
    }

    public Optional<List<Best>> getBestsForEventAndForTeamAndIsMensTeam(int eventId, int teamId, boolean isMensTeam) {
        return bestRepository.findByEventIdAndAthleteTeamIdAndAthleteIsMan(eventId,teamId,isMensTeam);
    }
}
