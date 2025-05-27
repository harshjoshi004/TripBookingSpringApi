FROM eclipse-temurin:21-jdk-alpine

# Install bash (needed for ./mvnw to run properly on Alpine)
RUN apk add --no-cache bash

WORKDIR /app

# Copy everything
COPY . .

# Ensure the wrapper script has execute permission
RUN chmod +x ./mvnw

# Run the Maven build
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

# Start the app using the built JAR
CMD ["java", "-jar", "target/trip-booking-api-0.0.1-SNAPSHOT.jar"]
