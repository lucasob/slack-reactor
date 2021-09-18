FROM gradle:7.1.1 as BUILD

WORKDIR /build

COPY ./ .

RUN gradle build

FROM openjdk:16.0.2-slim

ARG api
ARG token

ENV SLACK_API_URL=$api
ENV SLACK_AUTH_TOKEN=$token

WORKDIR /app

COPY --from=BUILD /build/build/libs/fingerGuns-0.0.1-all.jar ./fingerGuns.jar

EXPOSE 8080

CMD ["java", "-jar", "./fingerGuns.jar"]