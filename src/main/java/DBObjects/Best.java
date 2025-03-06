package DBObjects;

public class Best {
    public int bestID;
    public int eventId;
    public int athleteId;
    public String mark;
    public String link;

    public Best(int id, String mark, String link, int eventId, int athleteId){
        this.bestID = id;
        this.mark = mark;
        this.link = link;
        this.eventId = eventId;
        this.athleteId = athleteId;
    }
}
