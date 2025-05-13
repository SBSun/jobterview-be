package jobterview.api.subscription

import io.kotest.core.spec.style.FunSpec

class SubscriptionServiceTest : FunSpec({

//    val mailSender = mockk<MailSender>(relaxed = true)
//    val verifyMailTemplate = mockk<VerifyMailTemplate>()
//    val subscriptionRepository = mockk<SubscriptionJpaRepository>()
//    val mailVerificationRepository = mockk<MailVerificationJpaRepository>()
//    val jobService = mockk<JobService>()
//
//    val service = SubscriptionService(
//        mailSender, verifyMailTemplate, subscriptionRepository,
//        mailVerificationRepository, jobService
//    )
//
//    val email = "test@example.com"
//    val code = "123456"
//    val renderedTemplate = "<html>코드: $code</html>"
//
//    context("구독 인증 메일 발송") {
//        every { verifyMailTemplate.render(any()) } returns renderedTemplate
//        every { mailVerificationRepository.save(any()) } returnsArgument 0
//
//        test("인증 메일을 전송하고, 인증 정보를 저장한다.") {
//            service.sendVerifyEmail(email)
//
//            verify { mailSender.sendMail(email, any(), renderedTemplate) }
//            verify { mailVerificationRepository.save(match { it.email == email }) }
//        }
//    }
//
//    context("구독 요청") {
//        val jobId = UUID.randomUUID()
//        val request = SubscriptRequest(email = email, code = code, jobId = jobId)
//        val job = mockk<Job>()
//        val mailVerification = MailVerification(
//            email = email,
//            verifyCode = code,
//            expiredAt = LocalDateTime.now().plusMinutes(5)
//        )
//
//        every { mailVerificationRepository.findTopByEmailOrderByCreatedAtDesc(email) } returns Optional.of(mailVerification)
//        every { jobService.getById(jobId) } returns job
//        every { subscriptionRepository.save(any()) } returns mockk()
//
//        test("인증된 사용자에 대해 구독 정보를 저장한다") {
//            service.subscript(request)
//
//            verify { mailVerificationRepository.findTopByEmailOrderByCreatedAtDesc(email) }
//            verify { jobService.getById(jobId) }
//            verify { subscriptionRepository.save(match { it.email == email && it.job == job }) }
//        }
//    }
})