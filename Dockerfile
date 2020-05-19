FROM gradle:6.4.1-jdk11 as builder
WORKDIR /app
COPY . .
#don't execute tests because CI already executed them
RUN gradle clean build -x test

FROM openjdk:12-alpine
WORKDIR /opt
COPY --from=builder /app/core/build/libs ./
CMD ["/bin/sh","-c","java -jar finplat-api-*.jar"]