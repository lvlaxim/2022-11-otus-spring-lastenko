FROM bellsoft/liberica-openjdk-alpine-musl:17.0.2-9
COPY /target/library-docker.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
