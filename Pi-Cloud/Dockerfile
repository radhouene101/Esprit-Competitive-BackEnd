#Build stage
FROM maven:3.8.7-openjdk-18 as build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

#Runtime stage
FROM amazoncorretto:17
ARG PROFILE=dev
ARG APP_VERSION=1.0.0
ARG DB_IP=10.0.0.0
ARG DB_PORT=3306

WORKDIR /app
COPY --from=build /build/target/spring-jwt-*.jar /app/

RUN APP_VERSION=$(ls /app | grep *.jar | awk 'NR==2{split($0,a,"-"); print a[3]}' | awk '{sub(/.jar$/,"")}1')\
    && echo "Building container with BSN v-$version"
EXPOSE 8083

ENV DB_URL=jdbc:mysql://${DB_IP}:${DB_PORT}/db
ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}
ENV USER_DB=user
ENV USER_PASS_DB=password

CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL} -Dspring.database.user=${USER_DB} -Dspring.database.user=${USER_PASS_DB} spring-jwt-*.jar






