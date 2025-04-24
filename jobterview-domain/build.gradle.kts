plugins {
    id("spring-conventions")
}

dependencies {
    api(project(":jobterview-common"))

    api("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")

    api("com.fasterxml.uuid:java-uuid-generator:5.1.0")
}