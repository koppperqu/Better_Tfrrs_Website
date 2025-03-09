package DTOs;

import com.BetterTfrrsWebsite.UrlGenerator;

import java.io.UnsupportedEncodingException;

public class AthleteBestDTO {
    public String mark;
    public String eventName;
    public String tfrrsBestLink;
    public String eventURL;

    public AthleteBestDTO(String mark, String eventName, String tfrrsBestLink) throws UnsupportedEncodingException {
        this.mark = mark;
        this.eventName = eventName;
        this.tfrrsBestLink = tfrrsBestLink;
        UrlGenerator urlGenerator = new UrlGenerator();
        this.eventURL = urlGenerator.generateUrlNewParent(eventName,"events");
    }
}
