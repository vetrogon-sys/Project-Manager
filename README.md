Project-Manager
=

Repository contains example of to-do-application inspired Trello

Available modules
==

* `src-be` - backend entry point
* `src-fe` - frontend entry point
* `postgres-init` - sql scripts to initialize database tables and fill them with some data

Request data
==

All backend endpoints are documented with Swagger and available in `/api/swagger-ui/` endpoint

Building aps
==

By default:
* database start on port 5433
* backend start on port 8080
* frontend start on port 3000

To change in file `docker-compose.yml` change to necessary ports 

Building app using Docker
===

* fill `.env` file with required data
* from root folder run `docker-compose build` to build containers
* start database, for it run `docker-compose up postgresdb`
* run application using `docker-compose up`

Start application for development
===

* fill `.env` file with required data
* start database, for it run `docker-compose up postgresdb`

Start as Spring Boot application from IDE
====

* open `src-be` as maven module
* set up profiles to `local`
* run `main` method in `com.example.ProjectManagerApplication.java`

Start as Maven project
====

* open `src-be` directory
* in command line run `mvn spring-boot:run -Dspring.profiles.active=local`

Start frontend using docker
===

* from root folder run `docker-compose build pm-be` to build backend container
* start backend, for it run `docker-compose up pm-be`
* open `src-fe` directory
* in file `src.setupProxy.js` change host from `pm-be` to `localhost`
* run `npm install`
* run `npm start`

Start frontend without docker
===

* run backend application
* open `src-fe` directory
* in file `src.setupProxy.js` change host from `pm-be` to `localhost`
* run `npm install`
* run `npm start`