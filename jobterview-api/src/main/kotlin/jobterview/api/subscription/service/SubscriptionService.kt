package jobterview.api.subscription.service

import jobterview.api.job.service.JobService
import jobterview.api.subscription.request.SubscriptRequest
import jobterview.domain.mail.MailVerification
import jobterview.domain.mail.exception.MailVerificationException
import jobterview.domain.mail.repository.MailVerificationJpaRepository
import jobterview.domain.subscription.Subscription
import jobterview.domain.subscription.repository.SubscriptionJpaRepository
import jobterview.mail.MailSender
import jobterview.mail.template.VerifyMailTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.SecureRandom
import java.time.LocalDateTime


@Service
@Transactional
class SubscriptionService (
    private val mailSender: MailSender,
    private val verifyMailTemplate: VerifyMailTemplate,
    private val subscriptionRepository: SubscriptionJpaRepository,
    private val mailVerificationRepository: MailVerificationJpaRepository,
    private val jobService: JobService
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

    fun subscript(request: SubscriptRequest) {
        verify(request.email, request.code)

        val subscription = Subscription(
            email = request.email,
            job = jobService.getById(request.jobId)
        )

        subscriptionRepository.save(subscription)
    }

    private fun verify(email: String, code: String) {
        val mailVerification = mailVerificationRepository.findTopByEmailOrderByCreatedAtDesc(email)
            .orElseThrow { MailVerificationException.invalid() }

        if (mailVerification.isVerified || mailVerification.expiredAt.isBefore(LocalDateTime.now())) {
            throw MailVerificationException.expired()
        }

        if (mailVerification.verifyCode == code) {
            mailVerification.verified()
        } else {
            throw MailVerificationException.mismatch()
        }
    }

    private fun generateVerifyCode(): String {
        val number = SecureRandom().nextInt(1_000_000)
        return String.format("%06d", number)
    }
}