package jobterview.api.question.repository.custom

import jobterview.api.question.response.QuestionResponse
import jobterview.api.question.vo.QuestionFilter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomQuestionRepository {

    fun getQuestions(filter: QuestionFilter, pageable: Pageable): Page<QuestionResponse>

    fun getQuestionsByEmail(email: String, filter: QuestionFilter, pageable: Pageable): Page<QuestionResponse>
}