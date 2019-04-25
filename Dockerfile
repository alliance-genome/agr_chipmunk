FROM agrdocker/agr_base_linux_env:latest
  
RUN mkdir /data

WORKDIR /workdir/agr_fms_software

ADD . .

WORKDIR /workdir/agr_fms_software/src/main/cliapp

RUN npm run-script build

RUN mv build/* ../webapp

WORKDIR /workdir/agr_fms_software

RUN mvn -B clean package

