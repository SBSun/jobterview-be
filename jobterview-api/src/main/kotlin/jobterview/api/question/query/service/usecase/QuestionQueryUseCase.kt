package jobterview.api.question.query.service.usecase

import jobterview.api.question.response.QuestionDetailResponse
import jobterview.api.question.response.QuestionResponse
import jobterview.api.question.vo.QuestionFilter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import java.util.*

interface QuestionQueryUseCase {
    fun getDetail(id: UUID): QuestionDetailResponse
    fun getQuestions(filter: QuestionFilter, pageable: PageRequest): Page<QuestionResponse>
}