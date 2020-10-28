# competence_project

#### Required Software
* JDK 1.8
* Maven
* MongoDB and MongoDB Compass(optionally)

#### How To
* Run `mvn spring-boot:run` or `./mvnw spring-boot:run` or `mvnw.cmd spring-boot:run`
* Build `mvn clean install` or `./mvnw clean install` or `mvnw.cmd clean install`
* Checkstyle `mvn checkstyle:checkstyle` or `./mvnw checkstyle:checkstyle` OR `mvnw.cmd checkstyle:checkstyle`

#### Database Setup
* Remember to check setup in application.properties - setup below is the default one which you get if you run MongoDB installer
```
server.port=8080
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=competence_project_name
```

#### Important notes
* Please do not add Lombok because it is a heavy dependency and sometimes causes some problems

#### Useful links
* [MongoDB download](https://www.mongodb.com/try/download/community)
* [MongoDB installation manual](https://docs.mongodb.com/manual/administration/install-community/)
* [Creating database](https://www.mongodb.com/basics/create-database)
* [Spring with MongoDB guide](https://spring.io/guides/gs/accessing-data-mongodb/)
* [Spring Framework Guru MongoDB Guide](https://springframework.guru/configuring-spring-boot-for-mongo/)
* [Zetcode](http://zetcode.com/springboot/mongodb/)
* [Journaldev](https://www.journaldev.com/18156/spring-boot-mongodb)
* [Annotations](https://www.baeldung.com/spring-data-annotations)
* []()
