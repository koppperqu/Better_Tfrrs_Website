package DBObjects;

public class Athlete {
    public int id;
    public String name;
    public String link;
    public int teamId;
    public String grade;

    public Athlete(int id, String name, String link, int teamId, String grade){
        this.id = id;
        this.name = name;
        this.link = link;
        this.teamId = teamId;
        this.grade = grade;
    }
}
