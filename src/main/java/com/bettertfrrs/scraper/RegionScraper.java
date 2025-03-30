//package com.bettertfrrs.scraper;
//
//import com.bettertfrrs.db.entities.Team;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class RegionScraper {
//    //public Document conferenceDoc;
//    public List<String> teamNames;
//    public List<String> teamLinks;
//    public List<Boolean> isMensTeamBools;
//
//    //The main entry to the scraper, it gets passes the doc/thing it is scraping then
//    //it does the processing. This is how the other scrapers will be modeled to allow
//    //passing in of various different pages, teams, athletes etc.
//    public List<Team> scrapeConferencePage(Document conferenceDoc) {
//        Element teamsDiv = findTeamsDiv(conferenceDoc);
//        Element teamsTable = findTeamsTable(teamsDiv);
//        extractTeamNamesAndLinks(teamsTable);
//        return assembleDataIntoListOfTeams();
//    }
//
//    private List<Team> assembleDataIntoListOfTeams() {
//        List<Team> teams = new ArrayList<>();
//        for (int i = 0; i < teamNames.size(); i++){
//            Team team = new Team(
//                    0,
//                    teamNames.get(i),
//                    teamLinks.get(i),
//                    0,
//                    isMensTeamBools.get(i)
//            );
//            teams.add(team);
//        }
//        return teams;
//    }
//
//    private void extractTeamNamesAndLinks(Element teamsTable) {
//        List<String> listOfTeamNames  = new ArrayList<>();
//        List<String> listOfTeamLinks  = new ArrayList<>();
//        List<Boolean> listOfIsMensTeamBools = new ArrayList<>();
//        Element tableBody = teamsTable.getElementsByTag("tbody").first();
//        assert tableBody != null;
//        Elements links = tableBody.getElementsByTag("a");
//        //String linkHref = link.attr("href"); // "http://example.com/"
//        //String linkText = link.text(); // "example""
//        for (int i = 0; i<links.size(); i++){
//            Element link = links.get(i);
//            String tempTeamName = link.text();
//            if (tempTeamName.startsWith("Wis.-")) {
//                tempTeamName = tempTeamName.substring("WIS.-".length());
//            }
//            String tempTeamLink = link.attr("href");
//            //0 = mens team, 1 = womens team;
//            Boolean isMensTeam = (i % 2 == 0);
//            if (!tempTeamName.isEmpty() && !tempTeamLink.isEmpty()){
//                listOfTeamNames.add(tempTeamName);
//                listOfTeamLinks.add(tempTeamLink);
//                listOfIsMensTeamBools.add(isMensTeam);
//            }
//        }
//        teamNames = listOfTeamNames;
//        teamLinks = listOfTeamLinks;
//        isMensTeamBools = listOfIsMensTeamBools;
//    }
//
//    private Element findTeamsTable(Element teamsDiv) {
//        return teamsDiv.selectFirst("table");
//    }
//
//    private Element findTeamsDiv(Document doc){
//        //tableData=soup.find("h3", string="TEAMS").parent.find("table").findAll("a")
//        String findTeamsDivString = "h3:containsOwn(TEAMS)";
//        Element teamsH3 = doc.selectFirst(findTeamsDivString);
//        assert teamsH3 != null;
//        return teamsH3.parent();
//    }
//}
