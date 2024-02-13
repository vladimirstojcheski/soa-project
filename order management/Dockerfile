FROM maven:3.8.1-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY settings.xml /usr/share/maven/ref/

RUN mvn clean package -s /usr/share/maven/ref/settings.xml

FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY --from=build /app/target/order-management-0.0.1.jar .

CMD ["java", "-jar", "order-management-0.0.1.jar"]