package DTOs;

public class TeamDTO {
    public String name;
    public String mensLink;
    public String womensLink;
    public TeamDTO(String name){
        this.name = name;
    }
    public TeamDTO(String name, String mensLink, String womensLink){
        this.name = name;
        this.mensLink = mensLink;
        this.womensLink = womensLink;
    }
}
