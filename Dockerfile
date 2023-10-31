
FROM docker.io/maven:3-openjdk-18 AS builder

WORKDIR /usr/src
COPY . .


RUN mvn clean install -Dmaven.test.skip=true -Dmaven.wagon.http.ssl.insecure=true

FROM docker.io/openjdk:17.0.2-jdk-slim
#FROM docker.io/eclipse-temurin

# copy jar, and run.sh into /opt/app
COPY --from=builder /usr/src/target/*.jar /opt/app/electrica_users.jar
COPY run.sh /opt/app

# Create a group and user
RUN useradd -M -U app; \
       chown -R app:app /opt/app; \
       chmod -R u+rwx /opt/app;


EXPOSE 8086

USER app

CMD /opt/app/run.sh











