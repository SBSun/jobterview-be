plugins {
    id("application-conventions")
}

dependencies {
    implementation(project(":jobterview-domain"))
    implementation(project(":jobterview-mail"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
}