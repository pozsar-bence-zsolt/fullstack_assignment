FROM maven:3.9-amazoncorretto-21-alpine

WORKDIR /app

EXPOSE 35729
EXPOSE 6500

ENTRYPOINT ["mvn", "spring-boot:run", "-Dspring-boot.run.arguments=--debug"]