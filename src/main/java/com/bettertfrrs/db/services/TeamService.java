package com.bettertfrrs.db.services;

import com.bettertfrrs.db.entities.Team;
import com.bettertfrrs.db.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    public Team createOrUpdate(Team team) {
        Team teamInDB = getTeamByLink(team.link);
        if (teamInDB != null) {
            teamInDB.setName(team.name);
            teamInDB.setConference(team.conference);
            teamInDB.setHasMen(team.hasMen);
            return teamRepository.save(teamInDB);
        }else {
            return teamRepository.save(team);
        }
    }

    private Team getTeamByLink(String link) {
        return teamRepository.findByLink(link);
    }

    public Optional<List<Team>> getTeamsByConferenceId(Integer conferenceId) {
        return teamRepository.findByConferenceId(conferenceId);
    }

    public Optional<Team> getTeamByName(String teamName) {
        return teamRepository.findByName(teamName);
    }
}
