package DBObjects;

public class Event {
    public int id;
    public String name;
    public String short_name;

    public Event(int id, String name, String short_name){
        this.id = id;
        this.name = name;
        this.short_name = short_name;
    }
    //Alternate constructor since not all event need a name, those are added later in the DB manually.
    public Event(int id, String short_name){
        this.id = id;
        this.name = "";
        this.short_name = short_name;
    }
}
