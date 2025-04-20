package com.bettertfrrs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import com.bettertfrrs.scraper.PopulateAndUpdateDB;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class BetterTfrrsWebsiteApplication implements CommandLineRunner {

	@Autowired
	private PopulateAndUpdateDB scraper;

	public static void main(String[] args) {
		SpringApplication.run(BetterTfrrsWebsiteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (String arg : args) {
			if (arg.equalsIgnoreCase("scrape")) {
				scraper.run();
				System.exit(0);
			}
		}
	}
}
