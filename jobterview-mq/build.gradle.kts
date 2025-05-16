plugins {
    id("spring-conventions")
}

dependencies {
    implementation(project(":jobterview-common"))
    api(project(":jobterview-mail"))

    api("org.springframework.boot:spring-boot-starter-amqp")
}