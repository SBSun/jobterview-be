package jobterview.api

import jobterview.batch.config.MailAsyncConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication(
    scanBasePackages = ["jobterview.api", "jobterview.domain", "jobterview.mail", "jobterview.common"],
)
@Import(MailAsyncConfig::class)
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}