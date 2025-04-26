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
        athlete.setName(scrapeAthleteName(athletePage));
        athlete.grade =athleteHeaderText.substring(openParen+1,closeParen).trim();
        return athlete;
    }

    private String scrapeAthleteName(Document athletePage) {
        //https://www.tfrrs.org/athletes/8706314/Wis_Eau_Claire/McKenzie_Kruse.html
        String athleteLink = athletePage.location();
        int pos1 = athleteLink.lastIndexOf("/");
        int pos2 = athleteLink.lastIndexOf(".html");
        if (pos1 == -1 || pos2 == -1){
            throw new IllegalArgumentException("Unable to extract team name due to link format: " + athleteLink);
        }
        return athleteLink.substring(pos1+1,pos2).replace("_"," ");
    }
}
