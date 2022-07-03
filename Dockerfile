##FROM python:3.8
##
##RUN mkdir -p /usr/src/wiki-service
##WORKDIR /usr/src/wiki-service
##
##COPY . /usr/src/wiki-service
##
##RUN pip install -r requirements.txt
##RUN python import_data.py
#
#FROM openjdk:17
#
##RUN mkdir -p /app
##
##WORKDIR /app
#
##COPY . /app
##ENTRYPOINT [ "java","-jar","/app/build/libs/spring-project-0.1.0.jar" ]
#
##COPY . /usr/src/wiki-service
#
##ARG JAR_FILE=./target/wiki-service-0.0.1-SNAPSHOT.jar
##
#ADD ./target/wiki-service-0.0.1-SNAPSHOT.jar app.jar
##COPY ${JAR_FILE} /usr/src/wiki-serviceapp.jar
#
##ENV PATH=./src/main/resources/static
#
#EXPOSE 8080
#
##ENTRYPOINT ["java", "-jar", "/usr/src/wiki-service/target/wiki-service-0.0.1-SNAPSHOT.jar"]
#ENTRYPOINT ["java", "-jar", "app.jar"]
##ENTRYPOINT ["java", "-jar", "app.jar"]


FROM openjdk:17-jdk-alpine
#
##RUN mkdir app
##ADD target/wiki-service-0.0.1-SNAPSHOT.jar app/wiki-service-0.0.1-SNAPSHOT.jar
#
#ADD target/wiki-service-0.0.1-SNAPSHOT.jar /app.jar
#
##WORKDIR app
##RUN "pwd"
##
##RUN "ls"
#EXPOSE 8080
#
#ENTRYPOINT ["java","-jar", "/app.jar"]

#VOLUME /tmp
#
#COPY target/*.jar app.jar

ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]

#RUN "sudo chmod 777 app.jar"

#ENTRYPOINT ["java","-jar","/app.jar"]
