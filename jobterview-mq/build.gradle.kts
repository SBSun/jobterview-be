plugins {
    id("spring-conventions")
}

dependencies {
    api(project(":jobterview-mail"))

    api("org.springframework.boot:spring-boot-starter-amqp")
}