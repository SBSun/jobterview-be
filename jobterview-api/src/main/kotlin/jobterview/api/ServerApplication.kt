package jobterview.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = ["jobterview.api", "jobterview.domain"],
)
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}