package jobterview.api

import jobterview.batch.config.MailAsyncConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(
    scanBasePackages = ["jobterview.api", "jobterview.domain", "jobterview.mail", "jobterview.security", "jobterview.common"],
)
@EnableJpaRepositories(basePackages = ["jobterview.api"])
@Import(MailAsyncConfig::class)
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}