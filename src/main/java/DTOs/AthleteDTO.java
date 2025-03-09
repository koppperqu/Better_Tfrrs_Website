package DTOs;

import com.BetterTfrrsWebsite.UrlGenerator;

import java.io.UnsupportedEncodingException;

public class AthleteDTO {

    public String athleteName;
    public String athleteNameURLSafe;
    public String athleteURL;

    public AthleteDTO(String athleteName) throws UnsupportedEncodingException {
        this.athleteName = athleteName;
        UrlGenerator urlGenerator = new UrlGenerator();
        athleteNameURLSafe = urlGenerator.generateUrl(athleteName);
    }
}
