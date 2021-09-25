FROM openjdk:11
ADD ./target/instafeed-0.0.1-SNAPSHOT.jar /usr/src/instafeed-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "instafeed-0.0.1-SNAPSHOT.jar"]