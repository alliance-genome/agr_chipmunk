FROM agrdocker/agr_java_env:latest
  
WORKDIR /workdir/agr_fms_software

ADD . .

RUN mvn -B clean package

