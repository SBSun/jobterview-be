package jobterview.api.subscription.service

import jobterview.api.mail.MailSender
import jobterview.api.mail.template.VerifyMailTemplate
import jobterview.domain.mail.MailVerification
import jobterview.domain.mail.repository.MailVerificationJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.SecureRandom
import java.time.LocalDateTime


@Service
@Transactional
class SubscriptionService (
    private val mailSender: MailSender,
    private val verifyMailTemplate: VerifyMailTemplate,
    private val mailVerificationRepository: MailVerificationJpaRepository,
){

    fun sendVerifyEmail(email: String) {
        val subject = "이메일을 인증해주세요."
        val code = generateVerifyCode()
        val text = verifyMailTemplate.render(mapOf("code" to code))

        mailSender.sendMail(email, subject, text)

        val mailVerification = MailVerification(
            email = email,
            verifyCode = code,
            expiredAt = LocalDateTime.now().plusMinutes(5)
        )
        mailVerificationRepository.save(mailVerification)
    }

    fun verify(email: String, verifyCode: String) {
        val mailVerification = mailVerificationRepository.findByEmail(email)
            ?: throw IllegalArgumentException("유효하지 않은 인증 정보입니다.")

        if (mailVerification.verifyCode == verifyCode) {
            mailVerification.isVerified = true
        }
    }

    private fun generateVerifyCode(): String {
        val number = SecureRandom().nextInt(1_000_000) // 0 ~ 999999
        return String.format("%06d", number)
    }
}