FROM openjdk:alpine
RUN apk upgrade --update && \
    apk add --update g++ python3
