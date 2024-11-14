plugins {
    java
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

dependencies {

    // Web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Other
    implementation("org.springframework.boot:spring-boot-starter-validation")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    implementation ("org.jetbrains:annotations:16.0.2")

    // Persistence
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.liquibase:liquibase-core")
    implementation("net.lbruun.springboot:preliquibase-spring-boot-starter:1.5.1")
    runtimeOnly("org.postgresql:postgresql")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("com.redis.testcontainers:testcontainers-redis:1.6.4")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.springframework.security:spring-security-test")


    //security
    implementation("org.springframework.security:spring-security-core")
    implementation ("org.springframework.security:spring-security-config")
    implementation ("org.springframework.security:spring-security-web")
    implementation ("com.auth0:java-jwt:4.4.0")
}
tasks.withType<Test> {
    useJUnitPlatform()
}
