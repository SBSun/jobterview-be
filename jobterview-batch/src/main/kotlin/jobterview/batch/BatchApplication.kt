package jobterview.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication(
    scanBasePackages = ["jobterview.batch", "jobterview.domain", "jobterview.mail", "jobterview.common"],
)
@EnableJpaRepositories(basePackages = ["jobterview.batch"])
@EnableScheduling
class BatchApplication

fun main(args: Array<String>) {
    runApplication<BatchApplication>(*args)
}