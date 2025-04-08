package com.bettertfrrs.db.services;

import com.bettertfrrs.db.entities.Athlete;
import com.bettertfrrs.db.repositories.AthleteRepository;
import com.bettertfrrs.website.dtos.AthleteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AthleteService {
    private final AthleteRepository athleteRepository;

    @Autowired
    public AthleteService(AthleteRepository athleteRepository) {
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

    public List<AthleteDTO> getAthletesByTeamID(int teamID, Boolean isMensTeam) throws UnsupportedEncodingException {

        List<Athlete> athletes;
        if (isMensTeam == null){
            athletes = athleteRepository.findByTeamID(teamID);
        }else {
            athletes = athleteRepository.findByTeamIDAndIsMensTeam(teamID,isMensTeam);
        }

        List<AthleteDTO> athleteDTOs = new ArrayList<>();
        if (!athletes.isEmpty()) {
            for (Athlete athlete : athletes) {
                athleteDTOs.add(new AthleteDTO(athlete.name, athlete.id, teamID));
            }
        }

        return athleteDTOs;
    }
}
