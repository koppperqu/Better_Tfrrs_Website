package DBObjects;

public class Best {
    public int id;
    public String mark;
    public String link;
    public int event_id;
    public int athlete_id;

    public Best(int id, String mark, String link, int event_id, int athlete_id){
        this.id = id;
        this.mark = mark;
        this.link = link;
        this.event_id = event_id;
        this.athlete_id = athlete_id;
    }
}
