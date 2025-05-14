package jobterview.mq.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableRabbit
class RabbitMQConfig (
    @Value("\${spring.rabbitmq.host}")
    private val host: String,
    @Value("\${spring.rabbitmq.port}")
    private val port: Int,
    @Value("\${spring.rabbitmq.username}")
    private val username: String,
    @Value("\${spring.rabbitmq.password}")
    private val password: String
) {
    @Bean
    fun connectionFactory(): ConnectionFactory {
        val connectionFactory = CachingConnectionFactory(host, port)
        connectionFactory.username = username
        connectionFactory.setPassword(password)
        return connectionFactory
    }

    @Bean
    fun rabbitListenerContainerFactory(connectionFactory: ConnectionFactory): SimpleRabbitListenerContainerFactory {
        val factory = SimpleRabbitListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory)
        factory.setMessageConverter(Jackson2JsonMessageConverter())
        return factory
    }

    @Bean
    fun rabbitAdmin(): RabbitAdmin {
        return RabbitAdmin(connectionFactory())
    }

    @Bean
    fun rabbitTemplate(): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory())
        rabbitTemplate.messageConverter = Jackson2JsonMessageConverter()
        return rabbitTemplate
    }

    @Bean
    fun questionMailQueue(): Queue {
        return Queue("question_mail.queue", true)
    }

    @Bean
    fun resultQueue(): Queue {
        return Queue("result.queue", true)
    }

    @Bean
    fun exchange(): TopicExchange {
        return TopicExchange("question_mail.exchange")
    }

    @Bean
    fun questionMailBinding(questionMailQueue: Queue, exchange: TopicExchange): Binding {
        return BindingBuilder.bind(questionMailQueue)
            .to(exchange)
            .with("question_mail.send")
    }

    @Bean
    fun resultBinding(resultQueue: Queue, exchange: TopicExchange): Binding {
        return BindingBuilder.bind(resultQueue)
            .to(exchange)
            .with("result.*")
    }
}