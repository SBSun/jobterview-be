plugins {
    id("application-conventions")
}

dependencies {
    implementation(project(":jobterview-domain"))

    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-mail")

    testImplementation("org.springframework.batch:spring-batch-test")
}