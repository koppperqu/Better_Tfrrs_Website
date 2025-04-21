package com.bettertfrrs.scraper;

import com.bettertfrrs.db.entities.Athlete;
import com.bettertfrrs.db.entities.Team;
import org.jsoup.nodes.Document;

public class AthleteScraper {

    public Athlete scrapeAthlete(Document athletePage, Team team) {
        Athlete athlete = new Athlete();
        athlete.link = athletePage.location();
        athlete.team = team;
        String athleteHeaderText = athletePage.getElementsByClass("panel-title large-title").text();
        //The format expected here is "GAGE STANKIEWICZ (JR-3)"
        int openParen = athleteHeaderText.indexOf("(");
        int closeParen = athleteHeaderText.indexOf(")");
        athlete.name = athleteHeaderText.substring(0,openParen).trim();
        athlete.grade =athleteHeaderText.substring(openParen+1,closeParen).trim();
        return athlete;
    }
}
