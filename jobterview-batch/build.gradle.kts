dependencies {
    implementation(project(":jobterview-common"))
    implementation(project(":jobterview-domain"))

    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-mail")

    testImplementation("org.springframework.batch:spring-batch-test")
}

allOpen {
    annotation("org.springframework.context.annotation.Configuration")
    annotation("org.springframework.boot.context.properties.ConfigurationProperties")
}