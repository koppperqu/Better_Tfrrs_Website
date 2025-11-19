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

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Component
public class PopulateAndUpdateDB {

    //logging vars.
    private String currConference = null;
    private String currTeam = null;
    private String currAthlete = null;

    private final  AthleteService athleteService;
    private final  BestService bestService;
    private final  ConferenceService conferenceService;
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
        this.teamService = teamService;
        this.bestsScraper = new BestsScraper(eventService);
    }

    public void run() throws InterruptedException {
        try {
            System.out.println(LocalDateTime.now() + " Started database population");

            for (String conferenceLink : WIACandAmericaRivers) {
                currTeam = null;
                currAthlete = null;
                currConference = null;

                Document conferencePage = openHTMLPage(conferenceLink);
                Conference conference = processConference(conferencePage);
                currConference = conference.name;

                List<String> teamLinks = getConferencesTeamLinks(conferencePage);
                //Context - If a team has a mens and womens team I want the site to be able to display
                // Men and Women, Men, Women for either athletes or events. To be able to do this each team
                //has 2 vars to indicate if it has a mens team and if it has a womens team. The only thing that
                //changes in the URL is _m_ to _f_. Therefore each team only needs to be in the DB once to get
                //either url.
                List<String> teamLinksProcessed = new ArrayList<>();
                int i = 0;
                while (teamLinks.size() > teamLinksProcessed.size()) {
                    String teamLink = teamLinks.get(i);
                    if (!teamLinksProcessed.contains(teamLink)) {
                        Document teamPage = openHTMLPage(teamLink);
                        if (teamPage != null) {
                            Team teamFromHtml = teamScraper.scrapeTeam(teamPage, conference);
                            String oppositeGenderLink;
                            if (teamFromHtml.hasMen) {
                                oppositeGenderLink = teamFromHtml.link.replace("_m_", "_f_");
                            } else {
                                oppositeGenderLink = teamFromHtml.link.replace("_f_", "_m_");
                            }
                            if (teamLinks.contains(oppositeGenderLink)) {
                                //there are 2 genders for the team
                                teamFromHtml.hasMen = true;
                                teamFromHtml.hasWomen = true;
                            }
                            //We can add the team to the DB once we know if it has only one or both a men and womens team
                            Team team = createOrUpdateTeam(teamFromHtml);
                            currTeam = team.name;

                            //Process original teamPage we opened
                            List<String> athleteLinks = getTeamsAthleteLinks(teamPage);
                            boolean isMan = teamLink.contains("_m_");
                            processAthleteLinks(athleteLinks, team, isMan);
                            teamLinksProcessed.add(teamLink);
                            if (team.hasMen && team.hasWomen) {
                                //If there are both genders process the opposite gender link
                                isMan = !isMan;
                                teamPage = openHTMLPage(oppositeGenderLink);
                                athleteLinks = getTeamsAthleteLinks(teamPage);
                                processAthleteLinks(athleteLinks, team, isMan);
                                teamLinksProcessed.add(oppositeGenderLink);
                            }
                            currTeam = "Finished " + currTeam;
                        }
                    }
                    i++;
                }
                currConference = "Finished " + currConference;
            }
            //System.out.println("Finished scraping!");
            System.out.println(LocalDateTime.now() + " Finished database population");
        }catch (Exception e){
            String lastProcessed = String.format("Exception occurred during processing!\nConference: %s \nTeam: %s\nAthlete: %s",
                    currConference,currTeam,currAthlete);
            System.err.println(lastProcessed);
            System.out.println(lastProcessed);
            System.err.println(e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void processAthleteLinks(List<String> athleteLinks, Team team, boolean isMan) throws InterruptedException, IOException {
        for (int j = 0; j < athleteLinks.size(); j++) {
            String athleteLink = athleteLinks.get(j);
            Document athletePage = openHTMLPage(athleteLink);
            Athlete athlete = processAthlete(athletePage,team, isMan);
            currAthlete = athlete.name;
            processAthleteBests(athletePage, athlete);
            currAthlete = "Finished " + currAthlete;
        }
    }

    private Document openHTMLPage(String link) throws InterruptedException, IOException {
        for (int i = 0; i < 5; i++){
            try{
                return Jsoup.connect(link).get();
            } catch (Exception e) {
                if (i==4) {
                    throw(e);
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
