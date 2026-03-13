# Uso una imagen de Java 21 ligera
FROM eclipse-temurin:21-jdk-alpine

# Directorio de trabajo
WORKDIR /app

# Copio los archivos de Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

# Doy permisos y construyo el proyecto
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

# Expongo el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "build/libs/divertikids-backend-0.0.1-SNAPSHOT.jar"]