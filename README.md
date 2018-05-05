### Spring Boot Web with Flux/Mono interfaces

This is a simple project running with Spring Boot that has a CRUD for only one model. It uses MongoDB as Storage and the purpose is to show how we can develop simple API's using Reactor for web and repository layers.

#### Dependencies
- MongoDB;
- JDK9+;
- Gradle 4.5.1+;

#### How can I Run
- Start MongoDB(I'm using docker-ce): ```docker run -it -p27017:27017 mongo:3.4.9```
- ```cd ~/spring-netty-mongo-reactor-api```
- ```gradle bootRun```

#### Sample requests

- Get all persons
```
curl -X GET -v \
-H 'Accept: application/json' \
'http://localhost:8080/persons'
```

- Get one persons by id
```
curl -X GET -v \
-H 'Accept: application/json' \
'http://localhost:8080/persons/5aecfeecd251320f8c547346'
```

- Create a person
```
curl -X POST \
-H 'Accept: application/json' \
-H 'Content-Type: application/json' \
-d '{ "name": "name-1", "age": 1 }' \
'http://localhost:8080/persons'
```

- Update a person
```
curl -X PUT -v \
-H 'Accept: application/json' \
-H 'Content-Type: application/json' \
-d '{ "name": "name-1", "age": 1 }' \
'http://localhost:8080/persons/5aecfeecd251320f8c547346'
```

- Delete a person
```
curl -X DELETE -v \
'http://localhost:8080/persons/5aecfeecd251320f8c547346'
```
