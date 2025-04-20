package jobterview.api.mail

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component

@Component("questionMailSender")
class MailSender (
    private val mailSender: JavaMailSender,
) {

    fun sendMail(to: String, subject: String, text: String) {
        try {
            val message = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, true)

            helper.setTo(to)
            helper.setSubject(subject)
            helper.setText(text, true)

            mailSender.send(message)
        } catch (e: Exception) {
            println("메일 전송 실패: ${e.message}")
        }
    }
}