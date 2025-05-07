package jobterview.api.subscription.service

import com.fasterxml.uuid.Generators
import jobterview.api.job.service.JobService
import jobterview.api.subscription.request.SubscriptRequest
import jobterview.api.subscription.request.VerifyEmailRequest
import jobterview.domain.mail.MailToken
import jobterview.domain.mail.MailVerification
import jobterview.domain.mail.exception.MailVerificationException
import jobterview.domain.mail.repository.MailTokenJpaRepository
import jobterview.domain.mail.repository.MailVerificationJpaRepository
import jobterview.domain.subscription.Subscription
import jobterview.domain.subscription.exception.SubscriptionException
import jobterview.domain.subscription.repository.SubscriptionJpaRepository
import jobterview.mail.MailSender
import jobterview.mail.template.VerifyMailTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.SecureRandom
import java.time.LocalDateTime
import java.util.*


@Service
@Transactional
class SubscriptionService (
    private val mailSender: MailSender,
    private val verifyMailTemplate: VerifyMailTemplate,
    private val subscriptionRepository: SubscriptionJpaRepository,
    private val mailVerificationRepository: MailVerificationJpaRepository,
    private val mailTokenRepository: MailTokenJpaRepository,
    private val jobService: JobService
){

    fun sendVerifyEmail(request: VerifyEmailRequest) {
        jobService.verifyExist(request.jobId)
        if (subscriptionRepository.existsByEmailAndJob_Id(request.email, request.jobId)) {
            throw SubscriptionException.alreadySubscribed()
        }

        val subject = "이메일을 인증해주세요."
        val code = generateVerifyCode()
        val text = verifyMailTemplate.render(mapOf("code" to code))

        mailSender.sendMail(request.email, subject, text)

        val mailVerification = MailVerification(
            email = request.email,
            jobId = request.jobId,
            verifyCode = code,
            expiredAt = LocalDateTime.now().plusMinutes(5)
        )
        mailVerificationRepository.save(mailVerification)
    }

    fun subscript(request: SubscriptRequest) {
        verifyCode(request.email, request.jobId, request.code)

        val subscription = Subscription(
            email = request.email,
            job = jobService.getById(request.jobId)
        )

        subscriptionRepository.save(subscription)

        // 첫 구독일 경우, 메일 인증 토큰 생성
        if (!mailTokenRepository.existsById(request.email)) {
            val mailToken = MailToken(
                email = request.email,
                token = Generators.timeBasedEpochGenerator().generate().toString()
            )
            mailTokenRepository.save(mailToken)
        }
    }

    private fun verifyCode(email: String, jobId: UUID, code: String) {
        val mailVerification = mailVerificationRepository.findTopByEmailAndJobIdOrderByCreatedAtDesc(email, jobId)
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