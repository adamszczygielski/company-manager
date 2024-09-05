# building stage
FROM maven:3-eclipse-temurin-21 as builder

COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn package -Dmaven.test.skip

# deploying stage
FROM openjdk:21

COPY --from=builder /usr/src/app/target/*.jar app.jar

COPY entrypoint.sh .
RUN ["chmod", "777", "entrypoint.sh"]

ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,address=*:8787,server=y,suspend=n
ENTRYPOINT ["bash","entrypoint.sh"]

# service
EXPOSE 8080
# debug
EXPOSE 8787