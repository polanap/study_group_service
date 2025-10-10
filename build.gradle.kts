plugins {
    java
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"
description = "study_group_service"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("org.springframework.boot:spring-boot-starter-web") // Добавлено
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")
    implementation("org.springframework:spring-tx:6.2.10")
    implementation("jakarta.servlet:jakarta.servlet-api:6.1.0")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation ("org.springframework.boot:spring-boot-starter-thymeleaf")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
