package com.bettertfrrsscraper;

import com.bettertfrrsdb.DB;
import com.bettertfrrsdb.dbobjects.*;
import com.bettertfrrsdb.dbtables.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.HttpStatusException;
import java.util.concurrent.TimeUnit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

//1/29/25 Pivoting my approach to this project, initially I was going to flesh this out so it
//was implemented in a expandable way. Realied 2 things, 1 I don't care if it works for divisions outside
//of D3, I want to check on nationals standings and 2 I don't want to invest a lot of time into this. I have
//other things I would like to invest some time into. With that in mind I will make this functional to the point
//I need it to be while trying my best to maintain good practices. This will hopefully be enough to set the program
//up for expandability in the future if I want it.
public class PopulateAndUpdateDB {
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
    //static boolean firstTime = true;
    //Set to false for testing
    static DB db = null;
    static AthleteTable athleteTable = null;
    static BestsTable bestsTable = null;
    static EventsTable eventsTable = null;
    static TeamsTable teamsTable = null;
    static BestsScraper bestsScraper = new BestsScraper();
    static TeamScraper teamScraper = new TeamScraper();
    static AthleteScraper athleteScraper = new AthleteScraper();
    static RegionScraper regionScraper = new RegionScraper();
    //Hard code WIAC conference until decided to expand
    final static String WIACconference = "https://www.tfrrs.org/leagues/1420.html";
    //final String URL = "https://www.tfrrs.org/";
    final static String url = "jdbc:mysql://localhost:3306/track";
    final static String user = System.getenv("dbuser");
    final static String password = System.getenv("dbpass");

    public static void main(String[] args) {

        try {
            //Init db and tables
            // JDBC URL, username, and password of MySQL server

            db = new DB();
            athleteTable = new AthleteTable(db);
            bestsTable = new BestsTable(db);
            eventsTable = new EventsTable(db);
            teamsTable = new TeamsTable(db);
            checkConferences(WIACconference);
            //Get all teams and their athletes
            checkTeams();
            //Check the athletes for bests
            checkAthletesBests();
            System.out.println("Finished scraping");
            /* Commented out for now as it will just run everytime i start to debug.
            //Check athlete for bests every night at midnight.
            checkAthletesBests();
            //Check team data at the end of every week at midnight(sunday midnight?)
            //future maybe all teams in each conference for now it just checks wiac only needs to be checked on first time
            checkConferences();
            //Get all teams and their athletes
            checkTeams();
            */
            // Close the connection when done
            db.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // Close the connection when done, even if an exception occurred
            if (db != null) {
                db.close();
            }
        }
    }

    private static void checkConferences(String conferenceURL) throws IOException, SQLException {
        //just checks the wiac conference for now update if adding more
        Document conferencePage = Jsoup.connect(conferenceURL).get();
        List<Team> teamsNoIdOrConference = teamScraper.scrapeConferencePage(conferencePage);
        TeamsTable teamsTable = new TeamsTable(db);
        //Try to insert the teams into the DB is they dont exists already
        teamsTable.tryInsertTeams(teamsNoIdOrConference,1);
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
