package DTOs;

import com.BetterTfrrsWebsite.UrlGenerator;

import java.io.UnsupportedEncodingException;

public class EventDTO {

    private UrlGenerator urlGenerator = new UrlGenerator();
    public String eventName;
    public String eventNameURLSafe;
    public String eventURL;

    public EventDTO(String eventName) throws UnsupportedEncodingException {
        this.eventName = eventName;
        eventNameURLSafe = urlGenerator.generateUrl(eventName);
    }
}
