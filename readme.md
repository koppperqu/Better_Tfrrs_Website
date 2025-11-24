## What is this?
This is the code for the Java version of a website I created that aimed to enhance navigation of version of
https://www.tfrrs.org/ and only contain relevant data that I needed. 

## Origins:
This section is a little description of what the app started off as and how I morphed it into the website it 
is today. This project started in January 2022. I was in charge of the instagram throws page
(our subgroup of the track team shotput, weight, discus, hammer, javelin) for my college track team. One issue
I encounter while running this was gathering and keeping track of which teammates achieved a new personal record
(PR) each week. This required navigating through the TFRRS website and identifying who PR'd and then formatting it
into an instagram post. Additionally, we needed to let our throws coach know on which throw number and who PR'd so
we could post the associated videos. After doing this for a while I realized it seemed to be a good place to add
some automation. This is where the first iteration of the project came around. It simply grabbed the HTML and then
parsed and pulled out the necessary data. I would then organize and send off an email to myself and our throws 
coach with the necessary information to identify who PR'd and what throw it was on. 

This worked for the first year, but then I realized it would be nice to see what my thrower teammates PR's were 
at track meets. This is what lead to the first iteration of the website that was written in python and utilized
django as the web framework. Eventually I had that up and working and further realized, this would be incredibly
useful to be able to check on PR's for everyone on the team regardless of their event. So I set off to get the 
data for runners and jumpers as well. That lead to the final version of the python version of the website. This 
was stable and ran even after I graduated from college and was utilized by the track coach and old teammates that
still remained on the team. 

That leads into the creation of this Java version of the website. After having a real programming job for a while
I realized I missed my project and wanted to explore setting up in a language other than python. This is when I 
began porting the website to java utilizing the spring boot framework. I also wanted to explore expanding the 
website further and that's when I added the rest of the WIAC conference. Finally, my wife's sister was participating
in track in a different conference and I expanded the website to include the American Rivers Conference as well. 
That brings us to today and where this website stands. One part of the website utilizes some python code from 
another project. That simply gathers all teams PR's in the last 2 weeks and generates json that I parse and display.

Working on this project on and off for about 4 years taught me a lot about python, java, webframeworks, databases,
AWS, GCP, linux, and owning/managing a domain. 

*Note* the website is no longer live. I ran out of free time to try and keep it up. I have decided to freeze it in
the current state or until I get more time. Another issue if TFRRS has finally began blocking frequent requests
like scraping so that's another problem that would need to be addressed.

## Technical

### Building Requirements (at least what I used)
- Java 24 
- Windows 

### Build Instructions
1. Clone the repo 
2. Open terminal CD into the root folder of the repo
3. Run ".\gradlew build"
4. Inside build/libs there is BetterTfrrs.jar this is the file to start the scraper or website you can either leave it here or copy it into its own folder.

### Running Website or Scraper
Valid commands to run the jar are
- java -jar BetterTfrrs.jar
- java -jar BetterTfrrs.jar scrape

Scrape indicates to the jar to scrape the TFRRS pages for data and populate the database. When not in season
the team pages do not have data so no athletes may be scraped. The scrape process has on average taken 15-20
minutes depending on if TFRRS decides to start blocking requests. You can stop the process early and just 
have less data. During the offseason the scrape only takes about a minute as it just grabs the conferences
and teams.

The website is visible through localhost:8080

The database can be viewed through localhost/h2-console the JDBC URL should be "jdbc:h2:file:./data/bettertfrrsdb" remove H2 from the end.