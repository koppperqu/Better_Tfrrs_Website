What is this?
This is the code for the Java version of a website I created that aimed to enhance navigation of version of https://www.tfrrs.org/ and only contain relevant data that I needed. 

Origins:
This section is a little description of what the app started off as and how I morphed it into the website it is today. When I first embarked on this journey in January of 2022 I was in charge of the instagram throws page (our sub group of the track team shotput, weight, discus, hammer, javelin) for my college track team. One issue I encounter while running this was gathering and keeping track of which teammates achieved a new personal record (PR) each week. This required navigating through the TFRRS website and identifing who PR'd and then formatting it into an instagram post. Additionally we needed to let our throws coach know on which throw number and who PR'd so we could post the associated videos. After doing this for a while I realized it seemed to be a good place to add some automation. This is where the first iteration of the project came around. It simply grabbed the HTML and then parsed and pulled out the necessary data. I would then orginize and send off an email to myself and our throws coach with the necessary information to identify who PR'd and what throw it was on. 

This worked for the first year but then I realized it would be nice to see what my thrower teammates PR's were at track meets. This is what lead to the first iteration of the website that was written in python and utilized django as the web framework. Eventually I had that up and working and further realized, this would be incredibly useful to be able to check on PR's for everyone on the team reguardless of their event. So I set off to get the data for runners and jumpers as well. That lead to the final version of the python version of the website. This was stable and ran even after I graduated from college and was utilized by the track coach and old teammates that still remained on the team. 

That leads into the creation of this Java version of the website. After having a real programming job for a while I realized I missed my project and wanted to explore setting up in a language other than python. This is when I began porting the website to java utilzing the spring boot webframework. I also wanted to explore expanding the website further and thats when I added the rest of the WIAC conference. Finally my wife's sister was participating in track in a different conference and I expanded the website to include the American Rivers Conference as well. That brings us to today and where this website stands. There are still some aspects that utilize the old python code, partly due to lazyness and partly due to want to try to integrate 2 systems together.

Working on this project on and off for about 4 years taught me a lot about python, java, webframeworks, databases, AWS, GCP, linux, and owning/managing a domain. 

Technical

To build run 

Java 24

BetterTfrrsWebsite.jar
This is used to run the website, takes no arguments, ran with java -jar BetterTfrrsWebsite.jar 
BetterTfrrsDB

H2 using file persistance for the DB
Hibernate to interact with DB
Spring Boot for handling requests
ThymeLeaf to create the pages
JS to add functionality to pages

com.bettertfrrs.db
    entities - entites stored in the db
    repositories - interact with db
    services - handle and necessary interactions before CRUD
com.better.tfrrs.website
    dtos - send only necessary data to webpage

com.bettertfrrs.scraper
    


Valid commands

BetterTfrrs -refreshDB
BetterTfrrs -clearDB
BetterTfrrs -run
