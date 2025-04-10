ARG OVERWRITE_VERSION

FROM node:12 AS BUILD_UI_STAGE

WORKDIR /agr_fms

COPY src/main/cliapp ./cliapp

WORKDIR /agr_fms/cliapp
RUN make all build

FROM maven:3.8-openjdk-17 as BUILD_API_STAGE
ARG OVERWRITE_VERSION

WORKDIR /agr_fms/cliapp

COPY . .

COPY --from=BUILD_UI_STAGE /agr_fms/cliapp/build/index.html  ./src/main/resources/META-INF/resources/index.html
COPY --from=BUILD_UI_STAGE /agr_fms/cliapp/build/favicon.ico ./src/main/resources/META-INF/resources/favicon.ico
COPY --from=BUILD_UI_STAGE /agr_fms/cliapp/build/static/ ./src/main/resources/META-INF/resources/static/

# Optionally overwrite the application version stored in the pom.xml
RUN if [ "${OVERWRITE_VERSION}" != "" ]; then \
        mvn versions:set -ntp -DnewVersion=$OVERWRITE_VERSION; \
    fi;
# build the api jar
RUN cp src/main/resources/application.properties.defaults src/main/resources/application.properties

RUN mvn -T 8 clean package -Dquarkus.package.type=uber-jar -ntp

FROM openjdk:21-jdk-slim

WORKDIR /agr_fms

COPY --from=BUILD_API_STAGE /agr_fms/cliapp/target/agr_chipmunk-runner.jar .

# Expose necessary ports
EXPOSE 8080

# Set default env variables for local docker application execution

CMD ["java", "-jar", "agr_chipmunk-runner.jar"]
