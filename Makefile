all: build

run:
	java -jar target/agr_chipmunk-thorntail.jar -Papp.properties

build:
	mvn -T 6 clean package

apirun: build run


apidebug: build debug

debug:
	java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5045 -jar target/agr_chipmunk-thorntail.jar -Papp.properties
