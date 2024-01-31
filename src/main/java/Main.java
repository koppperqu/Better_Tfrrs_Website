import DB.DB;
import DBObjects.*;
import DBTables.*;
import Scrapers.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    //static boolean firstTime = true;
    //Set to false for testing
    static boolean firstTime = true;
    static DB db = null;
    static AthleteTable athleteTable = null;
    static BestsTable bestsTable = null;
    static EventsTable eventsTable = null;
    static TeamsTable teamsTable = null;
    static BestsScraper bestsScraper = new BestsScraper();
    static TeamScraper teamScraper = new TeamScraper();
    static AthleteScraper athleteScraper = new AthleteScraper();
    //Use this until adding more conferences.
    final static String URL = "https://www.tfrrs.org/leagues/1420.html";
    //final String URL = "https://www.tfrrs.org/";
    final static String url = "jdbc:mysql://localhost:3306/track";
    final static String user = System.getenv("dbuser");
    final static String password = System.getenv("dbpass");
    public static void main(String[] args) {

        try {
            //Init db and tables
            // JDBC URL, username, and password of MySQL server
            db = new DB(url,user,password);
            athleteTable = new AthleteTable(db);
            bestsTable = new BestsTable(db);
            eventsTable = new EventsTable(db);
            teamsTable = new TeamsTable(db);

            if (firstTime){
                //future maybe Gathers all conferences
                //checkNation();?
                //future maybe all teams in each conference for now it just checks wiac
                checkConferences();
                //Get all teams and their athletes
                checkTeams();
                //Check the athletes for bests
                checkAthletesBests();
                firstTime = false;
            }
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

    private static void checkConferences() throws IOException, SQLException {        //Opens the tffrs conference page for wiac
        //just checks the wiac conference for now update if adding more
        Document document = Jsoup.connect(URL).get();
        List<Team> teamsNoIdOrConference = teamScraper.scrapeConferencePage(document);
        TeamsTable teamsTable = new TeamsTable(db);
        //Try to insert the teams into the DB is they dont exists already
        teamsTable.tryInsertTeams(teamsNoIdOrConference,1);
    }

    private static void checkTeams() throws IOException, SQLException {
        //Retrieve all the teams in the db to then process each page for athletes
        List<Team> teams = teamsTable.getTeams();
        for (Team team : teams){
            //opens the team page
            Document document = Jsoup.connect(team.link).get();
            //scrape the athletes
            List<Athlete> athletesNoID = athleteScraper.scrapeTeamPage(document);
            //get the teams id
            int teamID = teamsTable.getTeamsWithTeamNameAndIsMensTeam(team.name, team.isMensTeam).get(0).id;
            //try to add them if they don't exist
            athleteTable.tryInsertAthletes(athletesNoID,teamID);
        }
    }

    private static void checkAthletesBests() throws SQLException, IOException {
        //Retrieve all athletes
        List<Athlete> athletes = athleteTable.getAthletes();
        //Scrap each athletes page for their bests
        for (Athlete athlete : athletes){
            Document document = Jsoup.connect(athlete.link).get();
            List<Best> bestsNoID = bestsScraper.scrapeAthletePage(document, eventsTable);
            int athlete_id = athleteTable.getAthleteWithAthleteNameAndTeamID(athlete.name,athlete.team_id).get(0).id;
            bestsTable.tryInsertBests(bestsNoID,athlete_id);
        }
    }
}