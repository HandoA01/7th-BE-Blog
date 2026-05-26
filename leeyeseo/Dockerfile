# 1단계: 빌드 스테이지 (Gradle로 JAR 생성)
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# Gradle wrapper와 빌드 설정 먼저 복사 (의존성 캐싱)
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon || true

# 소스 복사 및 빌드
COPY src src
RUN ./gradlew clean bootJar --no-daemon

# 2단계: 런타임 스테이지 (JRE만 포함하여 이미지 경량화)
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
