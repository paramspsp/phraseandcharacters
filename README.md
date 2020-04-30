# Character And Phrase Search Service

Provides simple REST APIs for searching Character and Phrases by using MDB Jquery user interface

## Dependencies:
* [JDK 1.8.x](http://www.oracle.com/technetwork/java/javase/overview/index.html)
* [Maven 3.x](https://maven.apache.org/index.html)
* [MDB JQuery ](https://mdbootstrap.com/docs/jquery/getting-started/download/) for MDB Jquery data tables
* Intellij IDE, [IntelliJ](https://www.jetbrains.com/idea/download/#section=mac) is heavily recommended for best Spring boot project development

## Setup:

1) Import the project or clone the project into an IDE either by clone or download option

2) Application will be running on the 8083 and make sure no other services are using this port. Kill the process Refer to [application.properties](resources/application.properties)    

3) Identifying the port usage and killing the process (Mac os)

    sudo lsof -i tcp:8083
    kill -9 <<Process ID>>   
    
4) Application will automatically load the data from JSON files called as characters.json and phrases.json into CHARACTER_TB and PHRASE_TB table in H2 database when the server is starting. Both these files are preent under /resources/data/ folder.

## REST APIs for Character and Phrase Services 

1. http://localhost:8083/api/v1/phrases/allPhrases (GET)
   
   Above API will return all the phrases from the database table called PHRASE_TB
 
2. http://localhost:8083/api/v1/phrases/specificPhrases/<<Phrase Word>> (GET)

   Above API will be returning the list of specific phrase word contains in the PHRASE_DB table.

3. http://localhost:8083/api/v1/phrases/addPhrase  (POST)

   Above API will be creating the new phrase record into the phrase table.
   
4. http://localhost:8083/api/v1/characters/allCharacters (GET)
   
   Above API will return all the characters from the database called as CHARACTER_TB
 
5. http://localhost:8083/api/v1/characters/specificCharactersByFirstName<<FirstName>>  (GET)

   Above API will be returning the list of specific characters where it matches the first name in the CHARACTER_DB table.

6. http://localhost:8083/api/v1/characters/specificCharactersByLastName<<LastName>>  (GET)

   Above API will be returning the list of specific characters where it matches the last name in the CHARACTER_DB table.

7. http://localhost:8083/api/v1/characters/addCharacter  (POST)

   Above API will be creating the new character record into the CHARACER_TB table.

## Using Services
The services may be accessed via HTTP requests. They return data in JSON format.

## User Interface to search character and phrases

Application can be accessed from the below home page URL. Home page will be displayng the two options to navigate to phrase or character search. Both these pages will display the MDB JQuery data table accordingly. Data tables will be having the pagination, search option and navigate back to home page.

http://localhost:8083/home.html






