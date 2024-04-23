FROM openjdk:21-jdk
WORKDIR /app
COPY /target/payment-system-0.0.1-SNAPSHOT.jar /app/payment-system-app.jar
CMD ["java", "-jar", "payment-system-app.jar"]