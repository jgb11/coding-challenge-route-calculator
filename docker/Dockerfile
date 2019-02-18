FROM openjdk:8-jdk-alpine

LABEL maintainer="Jose Luis Gonzalez Beltran <jlgonzalezbeltran@outlook.com>"

ENV HOME=/opt/jgb/
COPY *.jar ${HOME}

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/opt/jgb/coding-challenge-route-calculator-0.0.1-SNAPSHOT.jar"]
