plugins {
    java
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    id("io.freefair.lombok") version "8.6"
}

group = "com.crcp"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven { url = uri("https://m2.dv8tion.net/releases") } // JDA 用
}

dependencies {
    // Spring Boot (AOP含む)
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-aop")

    // JDA (Java Discord API)
    implementation("net.dv8tion:JDA:5.0.0-beta.20") // 安定版に近いベータ

    // AWS DynamoDB SDK (v2)
    implementation("software.amazon.awssdk:dynamodb:2.25.20")

    // Log4j2（Spring Bootと互換性のあるstarterを使用）
    implementation("org.springframework.boot:spring-boot-starter-log4j2")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // JUnit for testing
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}
