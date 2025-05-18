package jobterview.api.subscription.query.service

import jobterview.api.subscription.query.service.usecase.SubscriptionQueryUseCase
import jobterview.api.subscription.response.SubscriptionResponse
import jobterview.domain.mail.exception.MailTokenException
import jobterview.domain.mail.repository.MailTokenJpaRepository
import jobterview.domain.subscription.repository.SubscriptionJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SubscriptionQueryService (
    private val subscriptionRepository: SubscriptionJpaRepository,
    private val mailTokenRepository: MailTokenJpaRepository
) : SubscriptionQueryUseCase {

    /**
     * 이메일별 구독 정보 목록 조회
     *
     * @param email 이메일
     * @param token 이메일 인증 토큰
     * @return 구독 정보 응답 목록
     */
    override fun getSubscriptionsByEmail(email: String, token: String): List<SubscriptionResponse> {
        verifyToken(email, token)

        return subscriptionRepository.findAllByEmailOrderByCreatedAtDesc(email)
            .map { SubscriptionResponse(it) }
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