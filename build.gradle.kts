plugins {
    java
    id("com.gradleup.shadow") version "9.2.0"
    application
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
    // Lombok
    implementation("org.projectlombok:lombok:1.18.30")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    // web
    implementation("org.springframework:spring-webmvc:6.2.11")
    implementation("org.springframework:spring-context:6.2.11")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")

    // security
    implementation("org.springframework.security:spring-security-web:6.4.1")
    implementation("org.springframework.security:spring-security-config:6.4.1")

    // jpa/jdbc
    implementation("org.springframework:spring-orm:6.2.11")
    implementation("org.springframework:spring-jdbc:6.2.11")
    implementation("org.springframework.data:spring-data-jpa:3.3.5")
    implementation("org.hibernate.orm:hibernate-core:6.6.1")
    implementation("org.hibernate:hibernate-core:7.2.0.CR1")
    implementation("com.zaxxer:HikariCP:5.1.0")

    // thymeleaf
    implementation("org.thymeleaf:thymeleaf-spring6:3.1.2.RELEASE")

    // websocket
    implementation("org.springframework:spring-websocket:6.2.12")
    implementation("org.springframework:spring-messaging:6.2.12")
    implementation("org.eclipse.jetty.ee10.websocket:jetty-ee10-websocket-jetty-server:12.1.3")
    implementation("org.eclipse.jetty.websocket:jetty-websocket-jetty-api:12.1.3")

    // liquibase-core
    implementation("org.liquibase:liquibase-core:4.29.0")

    // jakarta.validation
    implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
    implementation("jakarta.validation:jakarta.validation-api:3.1.1")

    // Зависимости для базы данных
    runtimeOnly("org.postgresql:postgresql:42.7.3")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.13.0")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.13.0")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.13.0")

    // Тесты
    testImplementation("org.springframework:spring-test:6.2.11")
    testImplementation("org.springframework.security:spring-security-test:6.4.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.11.0")

    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")

    // embedded server
    implementation("org.eclipse.jetty:jetty-server:12.1.3")
    implementation("org.eclipse.jetty.ee10:jetty-ee10-servlet:12.1.3")

    // логирование
    implementation("ch.qos.logback:logback-classic:1.4.11")

    // Jakarta EL (для Hibernate Validator)
    implementation("jakarta.el:jakarta.el-api:6.0.1")
    implementation("org.glassfish:jakarta.el:5.0.0-M1")

    // JDBC pool (рекомендуется вместо DriverManagerDataSource)
    implementation("com.zaxxer:HikariCP:5.0.1")

    // PostgreSQL драйвер (уже у вас, убедитесь версия)
    runtimeOnly("org.postgresql:postgresql:42.6.0")

    runtimeOnly("org.aspectj:aspectjweaver:1.9.24")

}

application {
    mainClass.set("org.example.study_group_service.Main")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveBaseName.set("study-group-service")
    archiveClassifier.set("")
    archiveVersion.set(project.version.toString())
    mergeServiceFiles()
    manifest {
        attributes(mapOf(
            "Main-Class" to application.mainClass.get()
        ))
    }
}


//tasks.register("runServer") {
//    group = "application"
//    description = "clean -> shadowJar -> run the produced jar"
//    dependsOn(tasks.named("clean"), tasks.named("shadowJar"))
//    doLast {
//        val jar = tasks.named("shadowJar").get().outputs.files.singleFile
//        if (!jar.exists()) throw GradleException("Shadow JAR not found: ${jar}")
//        println("Running: java -jar ${jar}")
//        val process = ProcessBuilder("java", "-jar", jar.absolutePath)
//            .redirectErrorStream(true)
//            .start()
//        process.inputStream.bufferedReader().lines().forEach { println(it) }
//        val exit = process.waitFor()
//        if (exit != 0) throw GradleException("Process exited with $exit")
//    }
//}

tasks.build {
    dependsOn(tasks.named("shadowJar"))
}

