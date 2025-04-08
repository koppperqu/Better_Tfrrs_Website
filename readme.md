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