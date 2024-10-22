FROM adoptopenjdk/openjdk11
COPY ./*.jar /usr/app/
COPY ./.app-banner /usr/app/
WORKDIR /usr/app
ENV PORT=9500
ENV DB_USER=printer3d-user
ENV DB_PASSWORD=printer3d-password
RUN ls -la
EXPOSE 9500
ENTRYPOINT ["java", "-jar", "printer3d-backend-acceptance.jar"]
