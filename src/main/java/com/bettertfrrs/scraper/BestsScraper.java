package com.bettertfrrs.scraper;

import com.bettertfrrs.db.entities.Athlete;
import com.bettertfrrs.db.entities.Best;
import com.bettertfrrs.db.entities.Event;
import com.bettertfrrs.db.services.EventService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class BestsScraper {

    private final EventService eventService;

    public BestsScraper(EventService eventService) {
        this.eventService = eventService;
    }

    public List<Best> scrapeBests(Document athletePage, Athlete athlete) {
        List<Best> bests = new ArrayList<>();
        Element bestsTable = athletePage.getElementById("all_bests");
        //Some athletes have empty pr tables
        if (bestsTable != null) {
            Elements bestRows = bestsTable.getElementsByTag("tr");
            for (Element bestRow : bestRows) {
                Elements bestTDs = bestRow.getElementsByTag("td");
                for (int i = 0; i < bestTDs.size(); i += 2) {
                    Best best = new Best();
                    //Because of how these are formatted in teh table there are 4 TD's every 2 go together.
                    //this also means there can be an empty right hand side of 2 td's so we need to make
                    //sure its not empty.
                    String eventName = bestTDs.get(i).text().trim();
                    if (!eventName.isEmpty()) {
                        Element anchorTag = bestTDs.get(i + 1).getElementsByTag("a").first();
                        assert anchorTag != null : "Unable to find mark/link for pr";
                        best.link = anchorTag.attr("href");
                        best.mark = anchorTag.text();
                        best.event = processEvent(eventName);
                        best.athlete = athlete;
                        bests.add(best);
                    }
                }
            }
        }
        return bests;
    }

    private Event processEvent(String eventName) {
        Event event = new Event();
        event.shortName = eventName;
        return eventService.createOrUpdate(event);
    }
}
