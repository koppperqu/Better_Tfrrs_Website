package com.bettertfrrs.website.dtos;

import com.bettertfrrs.db.entities.Conference;
import com.bettertfrrs.db.entities.Team;
import com.bettertfrrs.db.services.TeamService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GlobalLinksDTO {

    public List<ConferenceDTO> conferenceDTOs = new ArrayList<>();

    public GlobalLinksDTO(List<Conference> conferences, TeamService teamService){
        for (Conference conference : conferences){
            Optional<List<Team>> teams = teamService.getTeamsByConferenceId(conference.id);
            if (teams.isPresent()){
                List<Team> ts = teams.get();
                conferenceDTOs.add(new ConferenceDTO(conference,ts));
            }
        }
    }
}
