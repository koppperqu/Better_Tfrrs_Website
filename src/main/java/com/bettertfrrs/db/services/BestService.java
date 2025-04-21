package com.bettertfrrs.db.services;

import com.bettertfrrs.db.entities.Best;
import com.bettertfrrs.db.entities.BestId;
import com.bettertfrrs.db.repositories.BestRepository;
import com.bettertfrrs.website.dtos.AthleteBestDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BestService {
    private final BestRepository bestRepository;

    public BestService(BestRepository bestRepository){
        this.bestRepository = bestRepository;
    }

    // Get all Bests
    public List<Best> getAllBests() {
        return bestRepository.findAll();
    }

    // Get a Best by Id
    public Optional<Best> getBestById(BestId id) {
        return bestRepository.findById(id);
    }

    // Create a new Best
    public Best createBest(String name) {
        Best Best = new Best();
        return bestRepository.save(Best);
    }

    public List<AthleteBestDTO> getBestsByAthleteId(int athleteId) {
        List<Best> bests = bestRepository.findByAthleteId(athleteId);
        List<AthleteBestDTO> athleteBestDTOs = new ArrayList<>();
        for (Best best : bests) {
            athleteBestDTOs.add(new AthleteBestDTO(best.mark, best.event.name, best.link, best.event.id, best.athlete.team.id));
        }
        return athleteBestDTOs;
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
}
