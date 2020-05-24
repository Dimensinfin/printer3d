FROM adoptopenjdk/openjdk11
COPY ./*.jar /usr/app/
WORKDIR /usr/app
RUN  ls -la
EXPOSE 9500
ENTRYPOINT ["java", "-Dspring.profiles.active=acceptance", "-jar", "printer3d-backend-acceptance.jar"]
