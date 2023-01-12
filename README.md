# Project-Manager

# Start project

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
    - ~~Start application from docker~~
      -
        - Coming soon

    - Start application from IntellijIDEA
      -
        - open `./src-be` folder as IntellijIDEA project
        - configure Build of application for it:
            - open *'edit configurations...'* menu
            - create new configuration with type *'Application'*
            - in *'main class'* input string
              select `src/main/java/com/example/ProjectManagerApplication.java`
            - enable `.env` files and add a path to `./.env` file in project root
            - activate *'local'* profile for it in VM options menu add following option:</br>
              `-Dspring.profiles.active=local`
        - run configured application

    - Start application as maven project
      -
        - in `./src-be/src/main/resources/application.properties` file `.env` data with values
          from `.env` file </br>
          For example:
           ```
           postgres.db.name=project-manager
           postgres.username=user
           postgres.password=admin
           security.jwt.token.secret-key=secret-key
           security.jwt.token.expire-length=3000
           ```
        - return to `./src-be` directory
        - open command line
        - run `mvn spring-boot:run -Dspring.profiles.active=local` command

