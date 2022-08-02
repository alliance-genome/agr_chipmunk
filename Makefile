RELEASE = production
REG = 100225593120.dkr.ecr.us-east-1.amazonaws.com
AWS_DEFAULT_REGION := us-east-1

NET?=production
ENV_NAME?=chipmunk-${NET}

GIT_VERSION = $(shell git describe --tags)

.PHONY: docker all

all: docker

clean:
	mvn clean

registry-docker-login:
ifneq ($(shell echo ${REG} | egrep "ecr\..+\.amazonaws\.com"),)
	@$(eval DOCKER_LOGIN_CMD=docker run --rm -it -v ~/.aws:/root/.aws amazon/aws-cli)
ifneq (${AWS_PROFILE},)
	@$(eval DOCKER_LOGIN_CMD=${DOCKER_LOGIN_CMD} --profile ${AWS_PROFILE})
endif
	@$(eval DOCKER_LOGIN_CMD=${DOCKER_LOGIN_CMD} ecr get-login-password --region=${AWS_DEFAULT_REGION} | docker login -u AWS --password-stdin https://${REG})
	${DOCKER_LOGIN_CMD}
endif

uirun:
	make -B -C src/main/cliapp
	make -B -C src/main/cliapp run

run: docker-run


apirun:
	mvn compile quarkus:dev

docker:
	docker build --build-arg OVERWRITE_VERSION=${GIT_VERSION} -t ${REG}/agr_chipmunk:${RELEASE} .
docker-push:
	docker push ${REG}/agr_chipmunk:${RELEASE}
docker-run:
	docker run --rm -it -p 8080:8080 --network=chipmunk ${REG}/agr_chipmunk:${RELEASE}

debug:
	java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5045 -jar target/agr_chipmunk_api-bootable.jar

test:
	mvn test

build:
	mvn -T 6 clean package

#EB commands
.PHONY: eb-init eb-create eb-deploy eb-terminate

eb-init:
	eb init --region us-east-1 -p Docker chipmunk-app

eb-create:
	@eb create ${ENV_NAME} --region=us-east-1 --cname="${ENV_NAME}" --elb-type application --envvars \
		AWS_SECRET_KEY=${AWS_SECRET_KEY},AWS_ACCESS_KEY=${AWS_ACCESS_KEY},AWS_BUCKET_HOST=${AWS_BUCKET_HOST},AWS_BUCKET_NAME=${AWS_BUCKET_NAME},QUARKUS_DATASOURCE_JDBC_URL=${QUARKUS_DATASOURCE_JDBC_URL},QUARKUS_DATASOURCE_USERNAME=${QUARKUS_DATASOURCE_USERNAME},QUARKUS_DATASOURCE_PASSWORD=${QUARKUS_DATASOURCE_PASSWORD},ENCRYPTION_PASSWORDKEY=${ENCRYPTION_PASSWORDKEY}

eb-deploy:
	@eb deploy ${ENV_NAME}

eb-terminate:
	@eb terminate ${ENV_NAME}
