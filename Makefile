all: build

run:
	java -jar target/agr_chipmunk-thorntail.jar -Papp.properties

build:
	mvn clean package

apirun: build run
