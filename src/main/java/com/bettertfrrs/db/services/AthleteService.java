package com.bettertfrrs.db.services;

import com.bettertfrrs.db.models.Athlete;
import com.bettertfrrs.db.repositories.AthleteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AthleteService {
    private final AthleteRepository athleteRepository;

    public AthleteService(AthleteRepository athleteRepository){
        this.athleteRepository = athleteRepository;
    }

    // Get all athletes
    public List<Athlete> getAllAthletes() {
        return athleteRepository.findAll();
    }

    // Get an athlete by ID
    public Optional<Athlete> getAthleteById(int id) {
        return athleteRepository.findById(id);
    }

    // Create a new athlete
    public Athlete createAthlete(String name) {
        Athlete athlete = new Athlete();
        athlete.setName(name);
        return athleteRepository.save(athlete);
    }
}
