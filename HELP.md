# Getting Started

#### Build

`mvnw clean package`

or

`mvn clean package`

#### Run with local with java

`java -jar target/GEA-HexTranslation-0.0.1-SNAPSHOT.jar`

[Swagger-UI](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/hex-translator-controller)


#### Create docker image and start container
`./docker.sh`

[Swagger-UI](http://localhost:8081/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/hex-translator-controller)

#### Clean out the docker container and image
`./docker-rm.sh`

#### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.0/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#web)

#### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

