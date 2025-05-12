plugins {
    id("application-conventions")
}

dependencies {
    implementation(project(":jobterview-domain"))
    implementation(project(":jobterview-mail"))
    implementation(project(":jobterview-security"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-cache")
}