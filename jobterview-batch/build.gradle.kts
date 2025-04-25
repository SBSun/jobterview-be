plugins {
    id("application-conventions")
}

dependencies {
    implementation(project(":jobterview-domain"))
    implementation(project(":jobterview-mail"))

    implementation("org.springframework.boot:spring-boot-starter-batch")
    testImplementation("org.springframework.batch:spring-batch-test")
}