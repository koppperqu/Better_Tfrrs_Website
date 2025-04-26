package com.bettertfrrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class BetterTfrrsWebsiteApplication {

	public static void main(String[] args) throws InterruptedException {
		for (String arg : args) {
			if (arg.equalsIgnoreCase("scrape")) {
				SpringApplication app = new SpringApplication(BetterTfrrsWebsiteApplication.class);
				app.setWebApplicationType(WebApplicationType.NONE); // disable web
				ConfigurableApplicationContext context = app.run(args);
				PopulateAndUpdateDB scraper = context.getBean(PopulateAndUpdateDB.class);
				scraper.run();
				return;
			}
		}
		SpringApplication.run(BetterTfrrsWebsiteApplication.class, args);
	}
}
