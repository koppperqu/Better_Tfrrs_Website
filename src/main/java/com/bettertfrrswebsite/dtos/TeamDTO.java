package DTOs;
import com.BetterTfrrsWebsite.UrlGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class TeamDTO {
    public String teamLink;

    public String name;
    public String mensLink;
    public String womensLink;

    public UrlGenerator urlGenerator = new UrlGenerator();

    public TeamDTO(String name) throws UnsupportedEncodingException {
        this.name = name;
        this.teamLink = urlGenerator.generateUrl(name);
    }
    public TeamDTO(String name, String mensLink, String womensLink) throws UnsupportedEncodingException {

        this.name = name;
        this.teamLink = urlGenerator.generateUrl(name);
        this.mensLink = mensLink;
        this.womensLink = womensLink;
    }
}
