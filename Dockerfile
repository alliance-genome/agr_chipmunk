FROM agrdocker/agr_java_env:latest
  
RUN mkdir /data

WORKDIR /workdir/agr_fms_software

ADD . .

RUN mvn -B clean package

