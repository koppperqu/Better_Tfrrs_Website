package com.bettertfrrs;

import com.bettertfrrs.db.entities.Athlete;
import com.bettertfrrs.db.entities.Best;
import com.bettertfrrs.db.entities.Conference;
import com.bettertfrrs.db.entities.Team;
import com.bettertfrrs.db.services.*;
import com.bettertfrrs.scraper.AthleteScraper;
import com.bettertfrrs.scraper.ConferenceScraper;
import com.bettertfrrs.scraper.TeamScraper;
import com.bettertfrrs.scraper.BestsScraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Component
public class PopulateAndUpdateDB {

    private final  AthleteService athleteService;
    private final  BestService bestService;
    private final  ConferenceService conferenceService;
    private final  EventService eventService;
    private final  TeamService teamService;

//    private final RegionScraper regionScraper = new RegionScraper();
    private final ConferenceScraper conferenceScraper = new ConferenceScraper();
    private final TeamScraper teamScraper = new TeamScraper();
    private final AthleteScraper athleteScraper = new AthleteScraper();
    private final BestsScraper bestsScraper;

    private final String[] WIACandAmericaRivers = {
            "https://www.tfrrs.org/leagues/1420.html", //WIAC
            "https://www.tfrrs.org/leagues/1400.html"  //American Rivers
    };
    //For if I ever decide to expand to all D3 instead of the 2 conferences
    private final String[] D3RegionLinks = {
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

    public PopulateAndUpdateDB(AthleteService athleteService, BestService bestService, ConferenceService conferenceService, EventService eventService, TeamService teamService) {
        this.athleteService = athleteService;
        this.bestService = bestService;
        this.conferenceService = conferenceService;
        this.eventService = eventService;
        this.teamService = teamService;

        this.bestsScraper = new BestsScraper(eventService);
    }

    public void run() throws InterruptedException {
        System.out.println("Starting database population...");
        // ANSI escape codes for cursor movement
        final String CLEAR_LINE = "\033[2K";
        final String MOVE_UP = "\033[1A";

        // Print four placeholder lines to start
        System.out.println("Time: ");
        System.out.println("Conference: ");
        System.out.println("Team: ");
        System.out.println("Athlete: ");

        // Timer start
        long programStart = System.currentTimeMillis();
        for (String conferenceLink : WIACandAmericaRivers) {
            Document conferencePage = openHTMLPage(conferenceLink);
            Conference conference = processConference(conferencePage);
            List<String> teamLinks = getConferencesTeamLinks(conferencePage);
            for (int i = 0; i < teamLinks.size(); i++) {
                String teamLink = teamLinks.get(i);
                Document teamPage = openHTMLPage(teamLink);
                Team team = processTeam(teamPage,conference.id);
                List<String> athleteLinks = getTeamsAthleteLinks(teamPage);
                for (int j = 0; j < athleteLinks.size(); j++) {
                    String athleteLink = athleteLinks.get(j);
                    Document athletePage = openHTMLPage(athleteLink);
                    Athlete athlete = processAthlete(athletePage,team);

                    // Move up 4 lines and clear them
                    System.out.print(MOVE_UP + CLEAR_LINE);
                    System.out.print(MOVE_UP + CLEAR_LINE);
                    System.out.print(MOVE_UP + CLEAR_LINE);
                    System.out.print(MOVE_UP + CLEAR_LINE);

                    // Print new info
                    long elapsed = System.currentTimeMillis() - programStart;
                    System.out.printf("Time: %.2f seconds%n", elapsed / 1000.0);
                    System.out.printf("Conference: %s%n", conference.name);
                    System.out.printf("Team (%d/%d): %s%n", i + 1, teamLinks.size(), team.name);
                    System.out.printf("Athlete (%d/%d): %s%n", j + 1, athleteLinks.size(), athlete.name);

                    processAthleteBests(athletePage, athlete);
                }
            }
        }
        System.out.println("Finished scraping!");
    }

    private Document openHTMLPage(String link) throws InterruptedException {
        for (int i = 0; i < 5; i++){
            int delay;
            if (i==0) {
                delay = 0;
            }else{
                delay = (int) (Math.pow(2, i-1) * 5);
            }
            //0 then 5 then 10 then 20 then 40
            TimeUnit.SECONDS.sleep(delay);
            try{
                return Jsoup.connect(link).get();
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    private Conference processConference(Document conferencePage){
        Conference conferenceFromHtml= conferenceScraper.scrapeConference(conferencePage);
        return conferenceService.createOrUpdate(conferenceFromHtml);
    }

    private List<String> getConferencesTeamLinks(Document conferencePage) {
        return conferenceScraper.scrapeTeamLinks(conferencePage);
    }


    private Team processTeam(Document teamPage, Integer conferenceId) {
        Team teamFromHtml = teamScraper.scrapeTeam(teamPage, conferenceId);
        return teamService.createOrUpdate(teamFromHtml);
    }

    private List<String> getTeamsAthleteLinks(Document teamPage) {
        return teamScraper.scrapeAthleteLinks(teamPage);
    }

    private Athlete processAthlete(Document athletePage, Team team) {
        Athlete athleteFromHtml = athleteScraper.scrapeAthlete(athletePage,team);
        return athleteService.createOrUpdate(athleteFromHtml);
    }

    private void processAthleteBests(Document athletePage, Athlete athlete) {
        List<Best> bestsFromHtml = bestsScraper.scrapeBests(athletePage,athlete);
        for(Best bestFromHtml : bestsFromHtml){
            Best best = bestService.createOrUpdateBest(bestFromHtml);
        }
    }

}
