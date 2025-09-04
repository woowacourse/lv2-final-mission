FROM openjdk:21-jdk
ARG SPRING_ACTIVE_PROFILE
ENV PROFILE=${SPRING_ACTIVE_PROFILE}
COPY build/libs/*SNAPSHOT.jar /level2-final.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "level2-final.jar"]
