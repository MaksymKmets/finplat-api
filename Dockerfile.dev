FROM maven:3.6.3-jdk-11 as builder
WORKDIR /app
COPY . .
RUN mvn package -DskipTests

FROM openjdk:12-alpine
WORKDIR /opt
COPY --from=builder /app/target/finplat.jar ./
CMD ["java", "-jar", "finplat.jar"]