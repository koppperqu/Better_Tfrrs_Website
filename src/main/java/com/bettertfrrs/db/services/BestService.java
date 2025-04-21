package com.bettertfrrs.db.services;

import com.bettertfrrs.db.entities.Best;
import com.bettertfrrs.db.entities.BestId;
import com.bettertfrrs.db.repositories.BestRepository;
import com.bettertfrrs.website.dtos.AthleteBestDTO;
import com.bettertfrrs.website.dtos.BestDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BestService {
    private final BestRepository BestRepository;

    public BestService(BestRepository BestRepository){
        this.BestRepository = BestRepository;
    }

    // Get all Bests
    public List<Best> getAllBests() {
        return BestRepository.findAll();
    }

    // Get a Best by Id
    public Optional<Best> getBestById(BestId id) {
        return BestRepository.findById(id);
    }

    // Create a new Best
    public Best createBest(String name) {
        Best Best = new Best();
        return BestRepository.save(Best);
    }

    public List<AthleteBestDTO> getBestsByAthleteId(int athleteId) {
        List<Best> bests = BestRepository.findByAthleteId(athleteId);
        List<AthleteBestDTO> athleteBestDTOs = new ArrayList<>();
        for (Best best : bests) {
            athleteBestDTOs.add(new AthleteBestDTO(best.mark, best.event.name, best.link, best.event.id, best.athlete.team.id));
        }
        return athleteBestDTOs;
    }
}
