# GithubRepoConnection
The application connects to the Github repository of a given user and selects selected data from it and then saves it to the database

Requirements:
- Java 17
- Spring 2.6.3
- MySQL
- Web browser or Postman

How to set application:
1. Load application with dependencies written in pom.xml
2. in src/main/resources/application.properties
    prompt your own login and password to your MySQL repository e.g.
   
    spring.datasource.username=your_login
    spring.datasource.password=your_password

How the app works:
1. when app is running, open your browser or Postman and call
http://localhost:8080/user/{username}
in {username} prompt name of user whose public repos you would like to check.
You will receive JSON with info:
- user name
- repository name
- description
- creation date in the format "dd-MM-yyyy HH:mm"
- number of stars
