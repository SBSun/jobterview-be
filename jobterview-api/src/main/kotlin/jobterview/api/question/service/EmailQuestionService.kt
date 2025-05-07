package jobterview.api.question.service

import jobterview.api.question.repository.QuestionRepository
import jobterview.api.question.response.QuestionResponse
import jobterview.api.question.vo.QuestionFilter
import jobterview.domain.mail.exception.MailTokenException
import jobterview.domain.mail.repository.MailTokenJpaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class EmailQuestionService (
    private val questionRepository: QuestionRepository,
    private val mailTokenRepository: MailTokenJpaRepository
) {

    fun getQuestionsByEmail(
        email: String,
        token: String,
        filter: QuestionFilter,
        pageable: PageRequest,
    ): Page<QuestionResponse> {
        val mailToken = mailTokenRepository.findById(email)
            .orElseThrow { MailTokenException.invalid() }

        if (mailToken.token != token) {
            throw MailTokenException.invalid()
        }

        return questionRepository.getQuestionsByEmail(email, filter, pageable)
    }
}