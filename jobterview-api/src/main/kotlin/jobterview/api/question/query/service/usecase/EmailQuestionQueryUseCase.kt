package jobterview.api.question.query.service.usecase

import jobterview.api.question.response.QuestionResponse
import jobterview.api.question.vo.QuestionFilter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface EmailQuestionQueryUseCase {
    fun getQuestionsByEmail(
        email: String,
        token: String,
        filter: QuestionFilter,
        pageable: PageRequest,
    ): Page<QuestionResponse>
}