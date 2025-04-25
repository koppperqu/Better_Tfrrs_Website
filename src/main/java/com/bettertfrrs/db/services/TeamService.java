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

    // Get all teams
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    // Get a team by ID
    public Optional<Team> getTeamById(int id) {
        return teamRepository.findById(id);
    }

    // Create a new team
    public Team createTeam(String name) {
        Team team = new Team();
        team.setName(name);
        return teamRepository.save(team);
    }

    public Team createOrUpdate(Team team) {
        Team teamInDB = getTeamByLink(team.link);
        if (teamInDB != null) {
            teamInDB.setName(team.name);
            teamInDB.setConferenceId(team.conferenceId);
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
