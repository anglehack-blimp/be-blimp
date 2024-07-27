FROM eclipse-temurin:21.0.2_13-jre as builder
WORKDIR extracted
COPY ./target/be-blimp-0.0.1-SNAPSHOT.jar blimp.jar
RUN java -Djarmode=layertools -jar blimp.jar extract

FROM  eclipse-temurin:21.0.2_13-jre
WORKDIR application
COPY --from=builder ./extracted/dependencies .
COPY --from=builder ./extracted/spring-boot-loader .
COPY --from=builder ./extracted/snapshot-dependencies .
COPY --from=builder ./extracted/application .
ENV APP_PORT=8080
EXPOSE $APP_PORT
CMD java org.springframework.boot.loader.launch.JarLauncher --server.port="$APP_PORT"
