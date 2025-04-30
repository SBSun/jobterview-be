plugins {
    id("spring-conventions")

    kotlin("kapt")
}

dependencies {
    api(project(":jobterview-common"))

    api("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")

    api("com.fasterxml.uuid:java-uuid-generator:5.1.0")

    api("com.querydsl:querydsl-jpa:5.1.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.1.0:jakarta")
}

kapt {
    arguments {
        arg("querydsl.entityAccessors", "true")
        arg("querydsl.createDefaultVariable", "true")
    }
}