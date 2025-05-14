package jobterview.batch.publisher

import jobterview.mq.request.MailSendRequest
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class MailPublisher(
    private val rabbitTemplate: RabbitTemplate
) {
    fun sendMail(to: String, subject: String, text: String) {
        val message = MailSendRequest(to, subject, text)
        rabbitTemplate.convertAndSend("question_mail.exchange", "question_mail.send", message)
    }
}