package com.BetterTfrrsWebsite;

import DB.DB;
import DBObjects.Best;
import DBObjects.Event;
import DBObjects.Team;
import DBTables.BestsTable;
import DBTables.EventsTable;
import DBTables.TeamsTable;
import DTOs.BestDTO;
import DTOs.EventDTO;
import DTOs.TeamDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;

@Controller
public class EventController {
    DB db = new DB();
    TeamsTable teamsTable = new TeamsTable(db);
    EventsTable eventsTable = new EventsTable(db);
    BestsTable bestsTable = new BestsTable(db);

    public UrlGenerator urlGenerator = new UrlGenerator();
    @GetMapping("/{teamName}/events")
    public String teamTest(@PathVariable String teamName, @RequestParam(required = false) String isMensTeam, Model model) throws SQLException, UnsupportedEncodingException {
        String decodedTeamName = urlDecode(teamName);
        List<Team> teamsList = teamsTable.safeGetTeamsWithName(decodedTeamName);
        if (!teamsList.isEmpty() && (Objects.equals(isMensTeam, "0") ||Objects.equals(isMensTeam, "1") || isMensTeam == null)){
        String teamNameURLSafe = urlGenerator.generateUrl(decodedTeamName);
        List<Event> eventsList = eventsTable.getEvents();
        List<EventDTO> eventDTOS = new ArrayList<>();
        for (Event event : eventsList){
            String nameToPass = "";
            //if (!event.name.isEmpty()) {
            //    nameToPass = event.name;
            //}
            //else{
                nameToPass = event.short_name;
            //}
            EventDTO eventDTO = new EventDTO(nameToPass);
            eventDTO.eventURL = "/"+teamNameURLSafe+"/events/"+eventDTO.eventNameURLSafe;
            if (Objects.equals(isMensTeam, "1")){
                eventDTO.eventURL += "?isMensTeam=1";
            } else if (Objects.equals(isMensTeam, "0")) {
                eventDTO.eventURL += "?isMensTeam=0";
            }
            eventDTOS.add(eventDTO);
        }
        model.addAttribute("teamName", decodedTeamName);
        model.addAttribute("eventDTOS", eventDTOS);
        //need to give the events page eventName and eventLink, event link will contain the isMensTeam var and team name
        return "teamEvents";
        }else {
            return "error";
        }
    }

    @GetMapping("/{teamName}/events/{eventName}")
    public String teamTest(@PathVariable String teamName,@PathVariable String eventName, @RequestParam(required = false) String isMensTeam, Model model) throws SQLException, UnsupportedEncodingException {
        String decodedTeamName = urlDecode(teamName);
        String decodedEventName = urlDecode(eventName);
        List<Team> teamsList;
        if (Objects.equals(isMensTeam, "1")){
            boolean isMensTeamBool = true;
            teamsList = teamsTable.getTeamsWithTeamNameAndIsMensTeam(decodedTeamName, isMensTeamBool);
        } else if (Objects.equals(isMensTeam, "0")) {
            boolean isMensTeamBool = false;
            teamsList = teamsTable.getTeamsWithTeamNameAndIsMensTeam(decodedTeamName, isMensTeamBool);
        }else {
            teamsList = teamsTable.safeGetTeamsWithName(decodedTeamName);
        }
        int teamID;
        int eventID;
        if (!teamsList.isEmpty()){
            if (teamsList.size()==1){
                teamID = teamsList.get(0).id;
            } else{
                teamID = -1;
            }
        }else{
            return "error";
        }
        List<Event> eventList = eventsTable.safeGetEventsIDWithShortName(decodedEventName);
        if (!eventList.isEmpty()){
            eventID = eventList.get(0).id;
        }else{
            return "error";
        }
        List<BestDTO> bestsDTOSList = new ArrayList<>();
        if (teamID == -1){
            bestsDTOSList = bestsTable.getBestsWithTeamNameAndEventID(decodedTeamName,eventID);
        }else {
            bestsDTOSList = bestsTable.getBestsWithTeamIDAndEventID(teamID,eventID);
        }
        //we need to query the bests table for bests join on athletes that are form teamname and for eventID
        //We need to give the page a list of bestDTOS that will have athlete name, mark, tfrrsLink for the mark
        //clicking on athlete name goes to their profile, clicking mark goes to the tfrrs page w/ the mark

        model.addAttribute("teamName", decodedTeamName);
        model.addAttribute("bestDTOS", bestsDTOSList);
        model.addAttribute("eventName", decodedEventName);
        return "eventBests";
    }

    private String urlDecode(String urlSafeString) throws UnsupportedEncodingException {
        return URLDecoder.decode(urlSafeString, StandardCharsets.UTF_8);
    }
}
