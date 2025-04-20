package jobterview.domain.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["jobterview.domain"])
@EnableJpaAuditing
@ComponentScan(basePackages = ["jobterview.domain"])
@EntityScan(basePackages = ["jobterview.domain"])
class DatabaseConfig {

}