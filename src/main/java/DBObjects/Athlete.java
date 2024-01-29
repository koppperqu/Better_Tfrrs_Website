package DBObjects;

public class Athlete {
    public int id;
    public String name;
    public String link;
    public int team_id;
    public String year;

    public Athlete(int id, String name, String link, int team_id, String year){
        this.id = id;
        this.name = name;
        this.link = link;
        this.team_id = team_id;
        this.year = year;
    }
}
