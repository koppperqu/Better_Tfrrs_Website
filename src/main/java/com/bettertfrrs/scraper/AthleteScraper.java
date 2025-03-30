//package com.bettertfrrs.scraper;
//
//import com.bettertfrrs.db.entities.Athlete;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class AthleteScraper {
//
//    public List<String> athleteNames;
//    public List<String> athleteLinks;
//    public List<String> athleteYears;
//    public List<Athlete> scrapeTeamPage(Document doc) {
//        Element rosterDiv = findRosterDiv(doc);
//        Element athleteTable = findAthleteTable(rosterDiv);
//        extractAthleteNamesLinksYears(athleteTable);
//        return assembleDataIntoListOfAthletes();
//    }
//    private List<Athlete> assembleDataIntoListOfAthletes() {
//        List<Athlete> athletes = new ArrayList<>();
//        for (int i = 0; i < athleteNames.size(); i++){
//            // Assuming DBObjects.Team class has a constructor that takes relevant fields
//            Athlete athlete = new Athlete(
//                    0,
//                    athleteNames.get(i),
//                    athleteLinks.get(i),
//                    0,
//                    athleteYears.get(i)
//            );
//            athletes.add(athlete);
//        }
//        return athletes;
//    }
//
//    private void extractAthleteNamesLinksYears(Element table){
//        athleteNames = new ArrayList<>();
//        athleteLinks = new ArrayList<>();
//        athleteYears = new ArrayList<>();
//        Element tbody = table.getElementsByTag("tbody").first();
//        assert tbody != null;
//        Elements athleteRows = tbody.getElementsByTag("tr");
//        for (Element athleteRow : athleteRows){
//            Elements tableData = athleteRow.getElementsByTag("td");
//            Element td1 = tableData.get(0);
//            Element anchorTag = td1.getElementsByTag("a").first();
//            assert anchorTag != null;
//            String athleteName = anchorTag.text();
//            athleteName = cleanAthleteName(athleteName);
//            //Add the tfrrs prefix because for some reason the links dont include it in the href....
//            String athleteLink = "https://www.tfrrs.org/" + anchorTag.attr("href");
//            Element td2 = tableData.get(1);
//            String athleteYear = td2.text();
//            athleteNames.add(athleteName);
//            athleteLinks.add(athleteLink);
//            athleteYears.add(athleteYear);
//        }
//    }
//
//    private String cleanAthleteName(String athleteName) {
//        String[] athleteNameParts = athleteName.split(",");
//        String tempName = "";
//        if (athleteNameParts.length == 2) { // Ensure there are two parts (last name, first name)
//            tempName = athleteNameParts[1].trim() + " " + athleteNameParts[0].trim();
//        } else {
//            // Handle invalid input or unexpected format
//            tempName = athleteName;
//        }
//        return tempName.trim();
//    }
//
//    private Element findAthleteTable(Element rosterDiv) {
//        return rosterDiv.selectFirst("table");
//    }
//
//    private Element findRosterDiv(Document doc){
//        //tableData=soup.find("h3", string="TEAMS").parent.find("table").findAll("a")
//        String findTeamsDivString = "h3:containsOwn(ROSTER)";
//        Element teamsH3 = doc.selectFirst(findTeamsDivString);
//        return teamsH3.parent();
//    }
//}
