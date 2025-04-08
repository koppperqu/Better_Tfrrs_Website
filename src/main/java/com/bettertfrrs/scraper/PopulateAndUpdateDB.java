package com.bettertfrrs.scraper;

import com.bettertfrrs.db.DB;
import com.bettertfrrs.db.entities.Athlete;
import com.bettertfrrs.db.entities.Best;
import com.bettertfrrs.db.entities.Conference;
import com.bettertfrrs.db.entities.Team;
import com.bettertfrrs.db.services.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.HttpStatusException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
public class PopulateAndUpdateDB {
    //For if I ever decide to expand to all D3 instead of the 2 conferences
    final static String[] D3RegionLinks = {
            "https://www.tfrrs.org/leagues/1435.html", //DIII All-Ohio
            "https://www.tfrrs.org/leagues/1612.html", //DIII East Region
            "https://www.tfrrs.org/leagues/1519.html", //DIII Great Lakes Region
            "https://www.tfrrs.org/leagues/1610.html", //DIII Metro Region
            "https://www.tfrrs.org/leagues/1520.html", //DIII Mid-Atlantic Region
            "https://www.tfrrs.org/leagues/1613.html", //DIII Mideast Region
            "https://www.tfrrs.org/leagues/1521.html", //DIII Midwest Region
            "https://www.tfrrs.org/leagues/255.html",  //DIII New England
            "https://www.tfrrs.org/leagues/1517.html", //DIII Niagara Region
            "https://www.tfrrs.org/leagues/1518.html", //DIII North Region
            "https://www.tfrrs.org/leagues/1522.html", //DIII South Region
            "https://www.tfrrs.org/leagues/1523.html", //DIII West Region
            "https://www.tfrrs.org/leagues/253.html"   //ECAC DIII
    };
    final static String[] WIACandAmericaRivers = {
            "https://www.tfrrs.org/leagues/1420.html", //WIAC
            "https://www.tfrrs.org/leagues/1400.html"  //American Rivers
    };


    private static AthleteService athleteService;
    private static BestService bestService;
    private static ConferenceService conferenceService;
    private static EventService eventService;
    private static TeamService teamService;

    public PopulateAndUpdateDB(AthleteService athleteService, BestService bestService, ConferenceService conferenceService, EventService eventService, TeamService teamService) {
        this.athleteService = athleteService;
        this.bestService = bestService;
        this.conferenceService = conferenceService;
        this.eventService = eventService;
        this.teamService = teamService;
    }

    public static void main(String[] args) {
        SpringApplication.run(PopulateAndUpdateDB.class, args);
    }

    //@Override
    public void run(String... args) throws Exception {
        System.out.println("Starting database population...");
        for (String conferenceLink : WIACandAmericaRivers) {
            processConference(conferenceLink);
            checkTeams();
            checkAthletesBests();
        }
        System.out.println("Finished scraping!");
    }


    static BestsScraper bestsScraper = new BestsScraper();
    static TeamScraper teamScraper = new TeamScraper();
    static AthleteScraper athleteScraper = new AthleteScraper();
    static RegionScraper regionScraper = new RegionScraper();
    static ConferenceScraper conferenceScraper = new ConferenceScraper();
    //Hard code WIAC conference until decided to expand
    final static String WIACconference = "https://www.tfrrs.org/leagues/1420.html";
    //final String URL = "https://www.tfrrs.org/";
    final static String url = "jdbc:mysql://localhost:3306/track";
    final static String user = System.getenv("dbuser");
    final static String password = System.getenv("dbpass");


    private static void processConference(String conferenceURL) throws IOException, SQLException {
        Document conferencePage = Jsoup.connect(conferenceURL).get();
        //We need to add a conference and get its ID
        Conference conference = conferenceScraper.scrapeConferenceINformation(conferencePage);
        //We need to then get all the teams in that conference and add those to the DB.
        List<Team> teamsNoIdOrConference = teamScraper.scrapeConferencePage(conferencePage);
        teamService.addTeams(teamsToAdd);
    }

    private static void checkTeams() throws IOException, SQLException {
        //Retrieve all the teams in the db to then process each page for athletes
        List<Team> teams = teamsTable.getTeams();
        for (Team team : teams){
            //opens the team page
            Document teamPage = Jsoup.connect(team.link).get();
            //scrape the athletes
            List<Athlete> athletesNoID = athleteScraper.scrapeTeamPage(teamPage);
            //get the teams id
            int teamID = teamsTable.getTeamsWithNameAndIsMensTeam(team.name, team.isMensTeam).get(0).id;
            //try to add them if they don't exist
            athleteTable.tryInsertAthletes(athletesNoID,teamID);
        }
    }

    private static void checkAthletesBests() throws SQLException, IOException, InterruptedException {
        //Retrieve all athletes
        List<Athlete> athletes = athleteTable.getAthletes();
        //Scrap each athletes page for their bests
        int amtAtheltes = athletes.size();
        int currAthlete = 0;
        for (Athlete athlete : athletes){
            currAthlete ++;
            System.out.println("Processing "+currAthlete+"/"+amtAtheltes+" "+ athlete.name);
            Document document;
            try{
                TimeUnit.MILLISECONDS.sleep(500);
                document = Jsoup.connect(athlete.link).get();
            } catch (HttpStatusException e) {
                System.out.println("HTTP Status Code " + e.getStatusCode()+" for athlete "+athlete.name+" trying again in 10 sec");
                //If there is an issue with the request wait 10 seconds and try again
                TimeUnit.SECONDS.sleep(10);
                try{
                    document = Jsoup.connect(athlete.link).get();
                } catch (HttpStatusException f) {
                    //try waiting for 30 seconds
                    System.out.println("HTTP Status Code " + f.getStatusCode()+" for athlete "+athlete.name+" trying again in 30 sec");
                    TimeUnit.SECONDS.sleep(30);
                    document = Jsoup.connect(athlete.link).get();
                }
            }
            List<Best> bestsNoID = bestsScraper.scrapeAthletePage(document, eventsTable);
            int athlete_id = athleteTable.getAthleteWithAthleteNameAndTeamID(athlete.name,athlete.teamId).get(0).id;
            bestsTable.tryInsertBests(bestsNoID,athlete_id);
        }
    }
}
