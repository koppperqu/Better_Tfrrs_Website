package DBObjects;

public class Best {
    public int eventId;
    public int athleteId;
    public String mark;
    public String link;

    public Best(String mark, String link, int eventId, int athleteId){
        this.mark = mark;
        this.link = link;
        this.eventId = eventId;
        this.athleteId = athleteId;
    }
}
