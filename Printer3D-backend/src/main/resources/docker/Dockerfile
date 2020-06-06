FROM adoptopenjdk/openjdk11
COPY ./*.jar /usr/app/
WORKDIR /usr/app
ENV PORT=9500
ENV JDBC_DATABASE_URL=jdbc:postgresql://postgres-dev:5432/postgres
ENV DB_USER=inno-user
ENV DB_PASSWORD=inno-password
RUN  ls -la
EXPOSE 9500
ENTRYPOINT ["java", "-Dspring.profiles.active=acceptance", "-jar", "printer3d-backend-acceptance.jar"]
