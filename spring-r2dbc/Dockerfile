### Builder
FROM eclipse-temurin:17.0.8.1_1-jdk-jammy AS build

# Receiving app version
ARG APP_VERSION=1.0.0

# Copy
WORKDIR /app
COPY pom.xml ./
COPY mvnw ./mvnw
COPY src ./src
COPY .mvn/ ./.mvn

RUN chmod +x mvnw

# Setting app version
RUN ./mvnw versions:set -DnewVersion=${APP_VERSION} -f pom.xml -DskipTests -Dtests.skip=true -Dskip.unit.tests=true && \
    ./mvnw versions:commit -f pom.xml -DskipTests -Dtests.skip=true -Dskip.unit.tests=true

# Build
RUN ./mvnw clean package -DskipTests -Dtests.skip=true -Dskip.unit.tests=true

### Deployer
FROM eclipse-temurin:17.0.8.1_1-jre-jammy AS deploy
ARG PORT=8080

# Copy
WORKDIR /app
COPY --from=build /app/target/dbcrypt.jar ./dbcrypt.jar

# User, port and health check
USER 1001
EXPOSE ${PORT}
HEALTHCHECK CMD curl -f http://localhost:${PORT}/actuator/health | grep '"status":"UP"'

# Startup
ENTRYPOINT ["java", "-jar","/app/dbcrypt.jar","--spring.profiles.active=container"]