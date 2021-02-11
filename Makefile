REG := 100225593120.dkr.ecr.us-east-1.amazonaws.com
DOCKER_IMAGE_TAG := latest

registry-docker-login:
ifneq ($(shell echo ${REG} | egrep "ecr\..+\.amazonaws\.com"),)
	@$(eval DOCKER_LOGIN_CMD=aws)
ifneq (${AWS_PROFILE},)
	@$(eval DOCKER_LOGIN_CMD=${DOCKER_LOGIN_CMD} --profile ${AWS_PROFILE})
endif
	@$(eval DOCKER_LOGIN_CMD=${DOCKER_LOGIN_CMD} ecr get-login-password | docker login -u AWS --password-stdin https://${REG})
	${DOCKER_LOGIN_CMD}
endif

all: build

run:
	java -jar target/agr_chipmunk-thorntail.jar -Papp.properties

build:
	mvn -T 6 clean package

apirun: build run

dockerbuild: registry-docker-login
	docker build --no-cache -t ${REG}/agr_fms_software:${DOCKER_IMAGE_TAG} --build-arg REG=${REG} --build-arg DOCKER_IMAGE_TAG=${DOCKER_IMAGE_TAG} .

apidebug: build debug

debug:
	java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5045 -jar target/agr_chipmunk-thorntail.jar -Papp.properties
