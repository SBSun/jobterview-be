package jobterview.api.question.query.service

import jobterview.api.question.query.service.usecase.EmailQuestionQueryUseCase
import jobterview.api.question.repository.QuestionRepository
import jobterview.api.question.response.QuestionResponse
import jobterview.api.question.vo.QuestionFilter
import jobterview.domain.mail.exception.MailTokenException
import jobterview.domain.mail.repository.MailTokenJpaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class EmailQuestionQueryService (
    private val questionRepository: QuestionRepository,
    private val mailTokenRepository: MailTokenJpaRepository
) : EmailQuestionQueryUseCase {

    /**
     * 이메일별 전송된 질문 목록 조회
     *
     * @param email 이메일
     * @param token 이메일 인증 토큰
     * @param filter 조회 조건
     * @param pageable 페이징 정보
     * @return 질문 정보 목록
     */
    override fun getQuestionsByEmail(
        email: String,
        token: String,
        filter: QuestionFilter,
        pageable: PageRequest
    ): Page<QuestionResponse> {
        val mailToken = mailTokenRepository.findById(email)
            .orElseThrow { MailTokenException.invalid() }

        if (mailToken.token != token) {
            throw MailTokenException.invalid()
        }

        return questionRepository.getQuestionsByEmail(email, filter, pageable)
    }
}