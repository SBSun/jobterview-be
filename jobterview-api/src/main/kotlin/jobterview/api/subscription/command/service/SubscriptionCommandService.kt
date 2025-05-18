package jobterview.api.subscription.command.service

import com.fasterxml.uuid.Generators
import jobterview.api.job.query.service.usecase.JobQueryUseCase
import jobterview.api.subscription.command.service.usecase.SubscriptionCommandUseCase
import jobterview.api.subscription.request.SubscriptRequest
import jobterview.api.subscription.request.VerifyEmailRequest
import jobterview.domain.mail.MailToken
import jobterview.domain.mail.MailVerification
import jobterview.domain.mail.exception.MailTokenException
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
class SubscriptionCommandService (
    private val mailSender: MailSender,
    private val verifyMailTemplate: VerifyMailTemplate,
    private val subscriptionRepository: SubscriptionJpaRepository,
    private val mailVerificationRepository: MailVerificationJpaRepository,
    private val mailTokenRepository: MailTokenJpaRepository,
    private val jobQueryUseCase: JobQueryUseCase
) : SubscriptionCommandUseCase {

    /**
     * 구독 인증 메일 발송
     *
     * @param request 구독 인증 메일 발송 요청 DTO
     */
    override fun sendVerifyEmail(request: VerifyEmailRequest) {
        jobQueryUseCase.verifyExist(request.jobId)
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

    /**
     * 구독
     *
     * @param request 구독 요청 DTO
     */
    override fun subscript(request: SubscriptRequest) {
        verifyCode(request.email, request.jobId, request.code)

        val subscription = Subscription(
            email = request.email,
            job = jobQueryUseCase.getById(request.jobId)
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

    /**
     * 구독 해제
     *
     * @param id 구독 ID
     * @param email 이메일
     * @param token 이메일 인증 토큰
     */
    override fun unsubscribe(id: UUID, email: String, token: String) {
        verifyToken(email, token)

        val subscription = subscriptionRepository.findByIdAndEmail(id, email)
            ?: throw SubscriptionException.notFound()

        subscriptionRepository.delete(subscription)
    }

    /**
     * 구독 인증 코드 생성
     */
    private fun generateVerifyCode(): String {
        val number = SecureRandom().nextInt(1_000_000)
        return String.format("%06d", number)
    }

    /**
     * 구독 인증 코드 검증
     */
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

    /**
     * 이메일 인증 토큰 검증
     */
    private fun verifyToken(email: String, token: String) {
        if (!mailTokenRepository.existsByEmailAndToken(email, token)) {
            throw MailTokenException.invalid()
        }
    }
}