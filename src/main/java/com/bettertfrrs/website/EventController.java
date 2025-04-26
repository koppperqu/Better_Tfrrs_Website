package com.bettertfrrs.website;

import com.bettertfrrs.db.entities.Best;
import com.bettertfrrs.db.entities.Conference;
import com.bettertfrrs.db.entities.Event;
import com.bettertfrrs.db.entities.Team;
import com.bettertfrrs.db.services.BestService;
import com.bettertfrrs.db.services.ConferenceService;
import com.bettertfrrs.db.services.EventService;
import com.bettertfrrs.db.services.TeamService;
import com.bettertfrrs.website.dtos.EventBestsDTO;
import com.bettertfrrs.website.dtos.EventsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.*;

@Controller
public class EventController {

    ConferenceService conferenceService;
    TeamService teamService;
    BestService bestService;
    EventService eventService;

    @Autowired
    public EventController(ConferenceService conferenceService, TeamService teamService, BestService bestService, EventService eventService){
        this.conferenceService = conferenceService;
        this.teamService = teamService;
        this.bestService = bestService;
        this.eventService = eventService;
    }
    @GetMapping("/events/{encodedConferenceName}/{encodedTeamName}")
    public String eventsForTeamPath(@PathVariable String encodedConferenceName, @PathVariable String encodedTeamName, @RequestParam(required = false) String isMensTeam, Model model) {
        String conferenceName = urlDecoder(encodedConferenceName);
        Optional<Conference> conference = conferenceService.getConferenceByName(conferenceName);
        String teamName = urlDecoder(encodedTeamName);
        Optional<Team> team = teamService.getTeamByName(teamName);
        if (conference.isPresent() && team.isPresent()) {
            Team t = team.get();
            Conference c = conference.get();
            Optional<List<Event>> events = Optional.empty();
            if (isMensTeam == null){
                events = bestService.getDistinctEventsForTeam(t.id);
            }else if (isMensTeam.equals("0")){
                events = bestService.getDistinctEventsForTeamAndIsMensTeam(t.id, false);
            }else if (isMensTeam.equals("1")){
                events = bestService.getDistinctEventsForTeamAndIsMensTeam(t.id, true);
            }
            if (events.isPresent()) {
                List<Event> e = events.get();
                EventsDTO eventsDTO = new EventsDTO(e, c, t, isMensTeam);
                model.addAttribute("teamName", t.name);
                model.addAttribute("eventsDTO", eventsDTO);
                //need to give the events page eventName and eventLink, event link will contain the isMensTeam var and team name
                return "eventsForTeam";
            }
        }
        return "404";
    }

    @GetMapping("/events/{encodedConferenceName}/{encodedTeamName}/{encodedEventName}")
    public String bestsForEventForTeam(@PathVariable String encodedConferenceName,@PathVariable String encodedTeamName, @PathVariable String encodedEventName, @RequestParam(required = false) String isMensTeam, Model model) {
        String conferenceName = urlDecoder(encodedConferenceName);
        Optional<Conference> conference = conferenceService.getConferenceByName(conferenceName);
        String teamName = urlDecoder(encodedTeamName);
        Optional<Team> team = teamService.getTeamByName(teamName);
        String eventName = urlDecoder(encodedEventName);
        Optional<Event> event = eventService.getEventByShortName(eventName);
        if (conference.isPresent() && team.isPresent() && event.isPresent()){
            Conference c = conference.get();
            Team t = team.get();
            Event e = event.get();
            Optional<List<Best>> bests = Optional.empty();
            if (isMensTeam == null){
                bests = bestService.getBestsForEventAndForTeam(e.id, t.id);
            }else if (isMensTeam.equals("0")){
                bests = bestService.getBestsForEventAndForTeamAndIsMensTeam(e.id, t.id, false);
            }else if (isMensTeam.equals("1")){
                bests = bestService.getBestsForEventAndForTeamAndIsMensTeam(e.id, t.id, true);
            }
            if (bests.isPresent()){
                List<Best> b = bests.get();
                EventBestsDTO eventBestsDTO = new EventBestsDTO(b,c,t);
                model.addAttribute("teamName", t.name);
                model.addAttribute("eventName", e.name);
                model.addAttribute("eventBestsDTO", eventBestsDTO);
                return "eventBests";
            }
        }
        return "404";
    }

    private String urlDecoder(String s){
        s = URLDecoder.decode(s, Charset.defaultCharset());
        return s;
    }
}
