# Project-Manager

# Start project

## Start project in production mode

- fill `.env` file with data </br>
  `.env` example:
  ```
   POSTGRES_USER=user
   POSTGRES_PASSWORD=admin
   POSTGRES_DB=project-manager
   PGADMIN_DEFAULT_EMAIL=admin@admin.com
   PGADMIN_DEFAULT_PASSWORD=admin
   JWT_TOKEN_SECRET=secret-key
   JWT_TOKEN_VALIDITY_IN_MILLISECONDS=3000
  ```
- from project root directory start database container. </br>`docker-compose up postgresdb`
- After container build run remaining containers. </br>`docker-compose up`

## Start project for development

- Start PostgreSQL From Docker:
  -
    - fill `.env` file with data
        - **if need in `docker-compose.yaml` file change postgresql or pgAdmin port**
    - run command `docker-compose up`
    - wait until docker start

  `.env` file example:
  ```
   POSTGRES_USER=user
   POSTGRES_PASSWORD=admin
   POSTGRES_DB=project-manager
   PGADMIN_DEFAULT_EMAIL=admin@admin.com
   PGADMIN_DEFAULT_PASSWORD=admin
   JWT_TOKEN_SECRET=secret-key
   JWT_TOKEN_VALIDITY_IN_MILLISECONDS=3000
  ```

- Start SpringBoot back-end application
    -

    - in `src-be/src/main/resources/application-local.properties` file 
      fill required fields with data from `.env` file </br> Example:
      ```
      postgres.port=5433
      postgres.db.name=project-manager
      postgres.username=user
      postgres.password=admin
      security.jwt.token.secret-key=secret-key
      security.jwt.token.expire-length=3000
      ```

    - Start application from IntellijIDEA
      -
        - open `./src-be` folder as IntellijIDEA project
        - configure *'Profiles'* of application for it:
            - open *'edit configurations...'* menu
            - create new configuration with type *'Application'*
            - choose `src/main/java/com/example/ProjectManagerApplication.java` as *'main class'*
            - activate *'local'* profile for it in VM options menu add following option:</br>
              `-Dspring.profiles.active=local`
        - run configured application

    - Start application as maven project
      -
        - open `./src-be` directory
        - open command line
        - run `mvn spring-boot:run -Dspring.profiles.active=local` command

