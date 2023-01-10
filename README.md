# Project-Manager

## Start project

- Start PostgrSQL From Docker:
   -
    - fill `.env` file with data
        - **if need in `docker-compose.yaml` file change postgresql or pgAdmin port**
    - run command `docker-compose up`
    - whait until docker start

   `.env` file example:
  ```
   POSTGRES_USER=user
   POSTGRES_PASSWORD=admin  
   POSTGRES_DB=project-manager 
   PGADMIN_DEFAULT_EMAIL=admin@admin.com
   PGADMIN_DEFAULT_PASSWORD=admin
  ```

- Start SpringBoot back-end application
    - 
     - Start SpringBoot application as java project - in `application.properties` file fill:
       ```
       postgres.db.name= 
       postgres.username=
       postgres.password=
       ```
       with values from `.env` file.</br>
       See Example
    
       ```
       postgres.db.name=project-manager
       postgres.username=user
       postgres.password=admin
       ```
   
    - Start application from IntellijIDEA
      -
        - open file `src/main/java/com/example/ProjectManagerApplication.java`
        - run `main` method in class
      
    - Start application as maven project
      -
        - go to `src-be` directory 
        - open command line
        - run `mvn spring-boot:run` command

  - ~~Start application from docker~~
    -
      - Coming soon