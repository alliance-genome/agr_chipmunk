all: build

run:
	java -jar target/agr_submission-swarm.jar -Papp.properties

build:
	mvn clean package

apirun: build run
