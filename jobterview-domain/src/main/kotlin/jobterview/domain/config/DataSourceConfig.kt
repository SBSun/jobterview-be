package jobterview.domain.config

import jakarta.persistence.EntityManagerFactory
import jobterview.domain.common.datasource.RoutingDataSource
import jobterview.domain.common.enums.DataSourceType
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(basePackages = ["jobterview.domain"])
@EnableJpaAuditing
@ComponentScan(basePackages = ["jobterview.domain"])
@EntityScan(basePackages = ["jobterview.domain"])
class DataSourceConfig {

    /**
     * 다중 DataSource 라우팅 구조에서 정확한 DataSource 선택을 보장하기 위해
     * LazyConnectionDataSourceProxy를 사용해 트랜잭션이 시작될 때까지 실제 커넥션 생성을 지연
     */
    @Bean
    fun dataSource(): DataSource {
        return LazyConnectionDataSourceProxy(routingDataSource())
    }

    @Bean
    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }

    @Bean(name = ["entityManagerFactory"])
    fun entityManagerFactory(dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSource
        em.setPackagesToScan("jobterview.domain")

        val vendorAdapter = HibernateJpaVendorAdapter()
        em.jpaVendorAdapter = vendorAdapter

        val properties = Properties()
        properties.setProperty("hibernate.jdbc.time_zone", "Asia/Seoul")
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
        em.setJpaProperties(properties)

        return em
    }

    /**
     * 읽기/쓰기 라우팅용 RoutingDataSource
     */
    @Bean(name = ["routingDataSource"])
    fun routingDataSource(): DataSource {
        val dataSourceMap: MutableMap<Any, Any> = ConcurrentHashMap()
        dataSourceMap[DataSourceType.WRITE] = writeDataSource()
        dataSourceMap[DataSourceType.READ] = readDataSource()

        val routingDataSource = RoutingDataSource().apply {
            setTargetDataSources(dataSourceMap)
            setDefaultTargetDataSource(readDataSource())
            afterPropertiesSet()
        }

        return routingDataSource
    }

    /**
     * Write DataSource
     */
    @Bean(name = ["writeDataSource"])
    @ConfigurationProperties("spring.datasource.write")
    fun writeDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    /**
     * Read DataSource
     */
    @Bean(name = ["readDataSource"])
    @ConfigurationProperties("spring.datasource.read")
    fun readDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }
}