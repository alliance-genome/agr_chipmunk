FROM node:12 AS BUILD_UI_STAGE

WORKDIR /agr_fms

COPY src/main/cliapp ./cliapp

WORKDIR /agr_fms/cliapp
RUN make all build

FROM maven:3.8-openjdk-11 as BUILD_API_STAGE

COPY . .

RUN cp src/main/resources/application.properties.defaults src/main/resources/application.properties

RUN mvn -T 8 clean package -Dquarkus.package.type=uber-jar -ntp


FROM openjdk:11-jre-slim

WORKDIR /agr_fms

COPY --from=BUILD_API_STAGE /target/agr_chipmunk-runner.jar .

# Expose necessary ports
EXPOSE 8080

# Set default env variables for local docker application execution

CMD ["java", "-Xmx8g", "-jar", "agr_chipmunk-runner.jar"]
