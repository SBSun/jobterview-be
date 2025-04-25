plugins {
    id("spring-conventions")
}

dependencies {
    implementation(project(":jobterview-common"))

    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
}