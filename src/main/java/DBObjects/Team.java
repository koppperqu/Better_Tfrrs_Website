package DBObjects;

public class Team {
    public int id;
    public String name;
    public String link;
    public int conferenceId;
    public boolean isMensTeam;

    public Team(int id, String name, String link, int conferenceId, Boolean isMensTeam) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.conferenceId = conferenceId;
        this.isMensTeam = isMensTeam;
    }
}