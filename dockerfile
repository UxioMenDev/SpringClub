FROM maven:3.9-eclipse-temurin-21

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY ./src ./src

RUN mvn clean package -DskipTests && \
    echo "Verificando contenido de target:" && \
    ls -la target/

EXPOSE 8080

CMD ["java", "-jar", "target/SpringClub-0.0.1-SNAPSHOT.jar"]