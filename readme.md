<h1>The Goal</h1>
I made a previous version of this website using Python and the Django framework.
There is an issue with how the data was being added to the database causing
duplicates of athletes. Instead of refactoring that code I decided to port
the code over to Java to get more experience building a project in Java.

I started this project at the end of 2023/start of 2024. Got busy and put it down.
Now I want to complete it and after coming back realized better documentation would
be helpful for myself in the future if I need to fix something again.

<h1>The Database</h1>
In the python version everything was built in Python. With this version I wanted to 
set up a separate SQL server to practice interacting with it. That is how this was 
set up. The table and columns that exist in the database are as follows.

**For now reference the DBObjects, will update later.

<h1>The Scraper</h1>
The scraper accesses HTMl from TFRRS. It extracts the data to get athletes, bests, teams etc.
It then updates the database as needed to keep it up to date. 
<ol>
  <li>Gather the data for a conference from TFRRS. (All teams in a conference)</li>
  <li>Add the teams/conference if necessary</li>
  <li>For each team, gather all athletes.</li>
  <li> Add athletes if they do not exist already</li>
  <li>For each athlete check/gather all their PR's</li>
  <li>add/update PR's if necessary.</li>
</ol>
  
tofinish

<h1>The Website</h1>
todo

For furture referencem
General idea is 
DTO to get data to website
DB Objects for all data that exists in the db so we can work with it
DB Tables are the classes used to interact with the tables. Adding, querying, etc.
Scraper gathers data from TFRRS so we have data in our db. 
1) Request comes in controller handle it
2) The correct method in the controller determines which query is needed to get the correct data to return from the DB.
3) DBTable classes have the query set and return the data in DBObjects. If a table is joined the data will be returned 2 lists of the approriate DBObject this is to prevent issues where we are querying one table but still need data from another.
4) The controller identifyies which data we need to send to the template in order to server the webpage and puts it into the DTO. 


The goal of this website is to make it easier to navigate through a teams athletes to identify 
their PR's. Additionally 


<H1>Scope Creep Adjustment</H1>
The main goal of this project is to get the website up and running. Expanding to more conference will be 
a later issue. For now I will focus on the WIAC conference <3. Will try to keep expanding and scalability in mind.