all: build

run:
	java -jar target/agr_chipmunk-thorntail.jar -Papp.properties

build:
	mvn -T 6 clean package

apirun: build run
