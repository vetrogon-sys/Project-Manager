FROM openjdk:11

WORKDIR /app/be

COPY .mvn/ .mvn
COPY mvnw ./
COPY pom.xml ./

RUN sed -i 's/\r$//' ./mvnw

RUN /bin/sh mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]