package jobterview.mq.consumer

import jobterview.common.utils.LogUtil
import jobterview.mail.MailSender
import jobterview.mq.request.MailSendRequest
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class MailConsumer (
    private val mailSender: MailSender
) {

    @RabbitListener(queues = ["question_mail.queue"])
    fun consume(message: MailSendRequest) {
        LogUtil.info("메일 전송 요청 수신: ${message.to}")
        mailSender.sendMail(message.to, message.subject, message.text)
    }
}