package com.bettertfrrs.db.services;

import com.bettertfrrs.db.entities.Athlete;
import com.bettertfrrs.db.repositories.AthleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AthleteService {
    private final AthleteRepository athleteRepository;

    @Autowired
    public AthleteService(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    public Athlete createOrUpdate(Athlete athlete) {
        Athlete athleteInDB = getAthleteByLink(athlete.link);
        if (athleteInDB != null) {
            athleteInDB.setName(athlete.name);
            athleteInDB.setTeam(athlete.team);
            athleteInDB.setGrade(athlete.grade);
            return athleteRepository.save(athleteInDB);
        }else {
            return athleteRepository.save(athlete);
        }
    }

    private Athlete getAthleteByLink(String link) {
        return athleteRepository.findByLink(link);
    }

    public Optional<List<Athlete>> getAthletesForTeam(int id) {
        return athleteRepository.findByTeamId(id);
    }

    public Optional<List<Athlete>> getAthletesForTeamAndIsMensTeam(int id, boolean b) {
        return athleteRepository.findByTeamIdAndIsMan(id,b);
    }

    public Optional<Athlete> getAthleteByNameAndTeam(String athleteName, int id) {
        return athleteRepository.findByNameAndTeamId(athleteName,id);
    }
}
