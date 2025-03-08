package DTOs;

import com.BetterTfrrsWebsite.UrlGenerator;

import java.io.UnsupportedEncodingException;

public class AthleteDTO {

    private UrlGenerator urlGenerator = new UrlGenerator();
    public String athleteName;
    public String athleteNameURLSafe;
    public String athleteURL;

    public AthleteDTO(String eventName) throws UnsupportedEncodingException {
        this.athleteName = eventName;
        athleteNameURLSafe = urlGenerator.generateUrl(eventName);
    }
}
