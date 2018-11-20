FROM maven:latest AS appserver
WORKDIR usr/src/student
COPY pom.xml .
RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
COPY . .
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml clean install -DskipTests

FROM java:8-jdk-alpine AS production
WORKDIR /app
COPY --from=appserver /usr/src/student/target/spring-boot-2-rest-service-basic-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java"]
CMD ["-jar", "/app/spring-boot-2-rest-service-basic-0.0.1-SNAPSHOT.jar"]