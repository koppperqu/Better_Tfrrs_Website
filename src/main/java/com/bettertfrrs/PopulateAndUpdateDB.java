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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Component
public class PopulateAndUpdateDB {

    private final  AthleteService athleteService;
    private final  BestService bestService;
    private final  ConferenceService conferenceService;
    private final  TeamService teamService;

//    private final RegionScraper regionScraper = new RegionScraper();
    private final ConferenceScraper conferenceScraper = new ConferenceScraper();
    private final TeamScraper teamScraper = new TeamScraper();
    private final AthleteScraper athleteScraper = new AthleteScraper();
    private final BestsScraper bestsScraper;

    // ANSI escape codes for cursor movement
    final String CLEAR_LINE = "\033[2K";
    final String MOVE_UP = "\033[1A";
    final String MOVE_DOWN = "\033[3B";
    // Timer start
    final long programStart = System.currentTimeMillis();

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
        this.teamService = teamService;
        this.bestsScraper = new BestsScraper(eventService);
    }

    public void run() throws InterruptedException {
        System.out.println("Starting database population...");

        // Print four placeholder lines to start
        System.out.println("Conference: ");
        System.out.println("Team: ");
        System.out.println("Athlete: ");
        System.out.println("Time: ");

        for (String conferenceLink : WIACandAmericaRivers) {
            Document conferencePage = openHTMLPage(conferenceLink);
            Conference conference = processConference(conferencePage);

            System.out.print(MOVE_UP + MOVE_UP + MOVE_UP + MOVE_UP + CLEAR_LINE);
            System.out.printf("Conference: %s%n" + MOVE_DOWN + MOVE_DOWN + MOVE_DOWN + MOVE_DOWN, conference.name);

            List<String> teamLinks = getConferencesTeamLinks(conferencePage);
            //Context - If a team has a mens and womens team I want the site to be able to display
            // Men and Women, Men, Women for either athletes or events. To be able to handle this each team
            //Will be stored with 2 bools for hasMen and/or hasWomen then each athlete will be set as a man
            //or woman. Therefore, we want to process teams that have the same name together. Only 1 team will
            //be made but each team will need to process its athletes.
            List<String> teamLinksProcessed = new ArrayList<>();
            int i = 0;
            while(teamLinks.size() > teamLinksProcessed.size()){
                String teamLink = teamLinks.get(i);
                if (!teamLinksProcessed.contains(teamLink)){
                    Document teamPage = openHTMLPage(teamLink);
                    if (teamPage != null) {
                        Team teamFromHtml = teamScraper.scrapeTeam(teamPage, conference);
                        String oppositeGenderLink;
                        if(teamFromHtml.hasMen){
                            oppositeGenderLink = teamFromHtml.link.replace("_m_","_f_");
                        }else{
                            oppositeGenderLink = teamFromHtml.link.replace("_f_","_m_");
                        }
                        if (teamLinks.contains(oppositeGenderLink)){
                            //there are 2 genders for the team
                            teamFromHtml.hasMen = true;
                            teamFromHtml.hasWomen = true;
                        }
                        //We can add the one team then we want to process the athletes from both
                        Team team = createOrUpdateTeam(teamFromHtml);

                        // Move up 4 lines and clear them
                        System.out.print(MOVE_UP + MOVE_UP + MOVE_UP + CLEAR_LINE);
                        System.out.printf("Team (%d/%d): %s%n" + MOVE_DOWN + MOVE_DOWN + MOVE_DOWN, i + 1, teamLinks.size(), team.name);

                        //Process original teamPage we opened
                        List<String> athleteLinks = getTeamsAthleteLinks(teamPage);
                        boolean isMan = teamLink.contains("_m_");
                        processAthleteLinks(athleteLinks,team,isMan);
                        teamLinksProcessed.add(teamLink);
                        if (team.hasMen && team.hasWomen){
                            //If there are both genders process the opposite gender link
                            isMan = !isMan;
                            teamPage = openHTMLPage(oppositeGenderLink);
                            athleteLinks = getTeamsAthleteLinks(teamPage);
                            processAthleteLinks(athleteLinks,team, isMan);
                            teamLinksProcessed.add(oppositeGenderLink);
                        }
                    }
                }
                i++;
            }
        }
        System.out.println("Finished scraping!");
    }

    private void processAthleteLinks(List<String> athleteLinks, Team team, boolean isMan) throws InterruptedException {
        for (int j = 0; j < athleteLinks.size(); j++) {
        String athleteLink = athleteLinks.get(j);
        Document athletePage = openHTMLPage(athleteLink);
        Athlete athlete = processAthlete(athletePage,team, isMan);

        System.out.print(MOVE_UP + CLEAR_LINE);
        System.out.print(MOVE_UP + CLEAR_LINE);

        // Print new info
        long elapsed = System.currentTimeMillis() - programStart;
        long seconds = (elapsed / 1000) % 60;
        long minutes = (elapsed / (1000 * 60)) % 60;

        System.out.printf("Athlete (%d/%d): %s%n", j + 1, athleteLinks.size(), athlete.name);
        System.out.printf("Time: %d:%02d%n", minutes, seconds);

        processAthleteBests(athletePage, athlete);
    }
    }

    private Document openHTMLPage(String link) throws InterruptedException {
        for (int i = 0; i < 5; i++){
            try{
                return Jsoup.connect(link).get();
            } catch (Exception e) {
                if (i==4) {
                    System.out.println(e.getMessage());
                    System.exit(1);
                }
            }
            int delay = (int) (Math.pow(2, i) * 10);
            //10, 20, 40, 80
            TimeUnit.SECONDS.sleep(delay);
        }
        System.out.println("Should not be here something wrong in code :(");
        return null;
    }

    private Conference processConference(Document conferencePage){
        Conference conferenceFromHtml= conferenceScraper.scrapeConference(conferencePage);
        return conferenceService.createOrUpdate(conferenceFromHtml);
    }

    private List<String> getConferencesTeamLinks(Document conferencePage) {
        return conferenceScraper.scrapeTeamLinks(conferencePage);
    }

    private Team createOrUpdateTeam(Team teamFromHtml) {
        return teamService.createOrUpdate(teamFromHtml);
    }

    private List<String> getTeamsAthleteLinks(Document teamPage) {
        return teamScraper.scrapeAthleteLinks(teamPage);
    }

    private Athlete processAthlete(Document athletePage, Team team, boolean isMan) {
        Athlete athleteFromHtml = athleteScraper.scrapeAthlete(athletePage,team);
        athleteFromHtml.isMan = isMan;
        return athleteService.createOrUpdate(athleteFromHtml);
    }

    private void processAthleteBests(Document athletePage, Athlete athlete) {
        List<Best> bestsFromHtml = bestsScraper.scrapeBests(athletePage,athlete);
        for(Best bestFromHtml : bestsFromHtml){
            Best best = bestService.createOrUpdateBest(bestFromHtml);
        }
    }

}
