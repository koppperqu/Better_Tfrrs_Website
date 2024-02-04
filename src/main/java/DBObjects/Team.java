package DBObjects;

public class Team {
    public int id;
    public String name;
    public String link;
    public int conference_id;
    public boolean isMensTeam;

    public Team(int id, String name, String link, int conference_id, boolean isMensTeam) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.conference_id = conference_id;
        this.isMensTeam = isMensTeam;
    }
}