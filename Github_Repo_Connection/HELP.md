# GithubRepoConnection

The application connects to the Github repository of a given user and selects selected data from it and then saves it to the database

Requirements:

    Java 17
    Spring 2.6.3
    MySQL
    Web browser or Postman

How to set application:

    Load application with dependencies written in pom.xml

    in src/main/resources/application.properties prompt your own login and password to your MySQL repository e.g.

    spring.datasource.username=your_login 
    spring.datasource.password=your_password

How the app works:

    when app is running, open your browser or Postman and call (GET) http://localhost:8080/user/{username} in {username} prompt name of user whose public repos you would like to check. You will receive JSON with info:

    user name
    repository name
    description
    creation date in the format "dd-MM-yyyy HH:mm"
    number of stars

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.3/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.3/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.3/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.6.3/reference/htmlsingle/#boot-features-jpa-and-spring-data)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

