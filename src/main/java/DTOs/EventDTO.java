package DTOs;

import com.BetterTfrrsWebsite.UrlGenerator;

import java.io.UnsupportedEncodingException;

public class EventDTO {

    public String eventName;
    public String eventNameURLSafe;
    public String eventURL;

    public EventDTO(String eventName) throws UnsupportedEncodingException {
        this.eventName = eventName;
        UrlGenerator urlGenerator = new UrlGenerator();
        eventNameURLSafe = urlGenerator.generateUrl(eventName);
    }
}
