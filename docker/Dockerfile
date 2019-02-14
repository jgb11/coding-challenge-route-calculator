FROM openjdk:8-jdk-alpine

LABEL maintainer="Jose Luis Gonzalez Beltran <jlgonzalezbeltran@outlook.com>"

VOLUME /opt/lib/

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/opt/lib/coding-challenge-route-calculator-0.0.1-SNAPSHOT.jar"]
