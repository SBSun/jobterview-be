plugins {
    id("kotlin-conventions")
    kotlin("plugin.allopen")
    kotlin("plugin.serialization")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

allOpen {
    annotation("org.springframework.context.annotation.Configuration")
    annotation("org.springframework.boot.context.properties.ConfigurationProperties")
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
