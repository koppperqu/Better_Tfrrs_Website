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
        //After running for a while sometimes TFRRS changes the link to an athlete,
        //due to this we need to change how we identify if we are updating or
        //adding an athlete. One reason we didnt use the name is TFRRS likes to
        //add random spaces which can also lead to an athlete being entered twice.
        //Going forward we will use name and team to identify athlete and do our
        //best to process the name to prevent duplicates.
        //Athlete athleteInDB = getAthleteByLink(athlete.link);
        Optional<Athlete> athleteInDB = getAthleteByNameAndTeam(athlete.name,athlete.team.id);
        if (athleteInDB.isPresent()) {
            athleteInDB.get().setName(athlete.name);
            athleteInDB.get().setTeam(athlete.team);
            athleteInDB.get().setGrade(athlete.grade);
            return athleteRepository.save(athleteInDB.get());
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
