package com.bettertfrrs.db.services;

import com.bettertfrrs.db.models.Team;
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

    // Get an team by ID
    public Optional<Team> getTeamById(int id) {
        return teamRepository.findById(id);
    }

    // Create a new team
    public Team createTeam(String name) {
        Team team = new Team();
        team.setName(name);
        return teamRepository.save(team);
    }
}
