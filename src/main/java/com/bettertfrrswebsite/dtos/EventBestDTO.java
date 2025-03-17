package DTOs;

import com.BetterTfrrsWebsite.UrlGenerator;

import java.io.UnsupportedEncodingException;

public class EventBestDTO {
    public String mark;
    public String athleteName;
    public String tfrrsBestLink;
    public String athleteURL;

    public EventBestDTO(String mark, String athleteName, String tfrrsBestLink) throws UnsupportedEncodingException {
        this.mark = mark;
        this.athleteName = athleteName;
        this.tfrrsBestLink = tfrrsBestLink;
        UrlGenerator urlGenerator = new UrlGenerator();
        this.athleteURL = urlGenerator.generateUrlNewParent(athleteName,"athletes");
    }
}
