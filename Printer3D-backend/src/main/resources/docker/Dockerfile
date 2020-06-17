FROM adoptopenjdk/openjdk11
COPY ./*.jar /usr/app/
WORKDIR /usr/app
ENV PORT=9500
#ENV JDBC_DATABASE_URL=jdbc:postgresql://postgres-acceptance:5432/postgres
ENV DB_USER=printer3d-user
ENV DB_PASSWORD=printer3d-password
RUN  ls -la
EXPOSE 9500
ENTRYPOINT ["java", "-jar", "printer3d-backend-acceptance.jar"]
