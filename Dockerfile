ARG DOCKER_IMAGE_TAG=latest
ARG REG=100225593120.dkr.ecr.us-east-1.amazonaws.com

FROM ${REG}/agr_base_linux_env:${DOCKER_IMAGE_TAG}
  
RUN mkdir /data

WORKDIR /workdir/agr_fms_software

ADD . .

WORKDIR /workdir/agr_fms_software/src/main/cliapp

RUN /bin/bash -c '. $HOME/.nvm/nvm.sh --no-use && \
  nvm install && \
  nvm use && \
  npm install'

RUN /bin/bash -c '. $HOME/.nvm/nvm.sh && npm run-script build'

RUN mv build/* ../webapp

WORKDIR /workdir/agr_fms_software

RUN mvn -T 6 -B clean package

EXPOSE 8080
