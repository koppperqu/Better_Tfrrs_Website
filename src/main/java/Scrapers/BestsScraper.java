package Scrapers;

import DBObjects.Best;
import DBObjects.Event;
import DBTables.EventsTable;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BestsScraper {
    public List<String> eventShortNames;
    public List<String> bestLinks;
    public List<String> bestMarks;
    private EventsTable eventsTable;
    //Bests table has id = all_bests and class = table bests
    public List<Best> scrapeAthletePage(Document doc, EventsTable eventsTable) throws SQLException {
        //Need to pass in events table to be able to get event id
        this.eventsTable = eventsTable;
        Element bestsTable = findBestsTable(doc);
        extractAthleteEventNamesMarksLinks(bestsTable);
        return assembleDataIntoListOfBests();
    }

    private void extractAthleteEventNamesMarksLinks(Element bestsTable) {
        eventShortNames = new ArrayList<>();
        bestLinks = new ArrayList<>();
        bestMarks = new ArrayList<>();
        Elements tableData = bestsTable.getElementsByTag("td");
        for (int i = 0; i<tableData.size(); i += 2){
            //i is the event name, i + 1 is the event mark
            //if its not an empty td process otherwise its skipped
            if (!tableData.get(i).text().trim().isEmpty()) {
                String eventName = tableData.get(i).text().trim();
                Element anchorTag = tableData.get(i + 1).getElementsByTag("a").first();
                String bestLink = anchorTag.attr("href");
                String bestMark = anchorTag.text();
                eventShortNames.add(eventName);
                bestLinks.add(bestLink);
                bestMarks.add(bestMark);
            }
        }
    }
    private List<Best> assembleDataIntoListOfBests() throws SQLException {
        List<Best> bests = new ArrayList<>();
        for (int i = 0; i < eventShortNames.size(); i++){
            Event event = new Event(0, eventShortNames.get(i));
            int eventID = eventsTable.getEventsIDWithShortName(event);
            Best best = new Best(
                    0,
                    bestMarks.get(i),
                    bestLinks.get(i),
                    eventID,
                    0
            );
            bests.add(best);
        }
        return bests;
    }

    private Element findBestsTable(Document doc) {
        return doc.selectFirst("#all_bests");
    }
}
