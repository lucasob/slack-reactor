FROM gradle:7.1.1 as BUILD

WORKDIR /build

COPY ./ .

RUN gradle build

FROM openjdk:16.0.2-slim

WORKDIR /app

COPY --from=BUILD /build/build/libs/fingerGuns-0.0.1-all.jar ./fingerGuns.jar

ENTRYPOINT ["java", "-jar", "./fingerGuns.jar"]