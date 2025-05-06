package jobterview.api.question.service

import jobterview.api.question.repository.QuestionRepository
import jobterview.api.question.response.QuestionDetailResponse
import jobterview.api.question.response.QuestionResponse
import jobterview.api.question.vo.QuestionFilter
import jobterview.domain.question.Question
import jobterview.domain.question.exception.QuestionException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class QuestionService (
    private val questionRepository: QuestionRepository
){

    @Transactional(readOnly = true)
    fun getQuestions(
        filter: QuestionFilter,
        page: Int,
        size: Int
    ): Page<QuestionResponse> {
        val pageable = PageRequest.of(page, size)

        return questionRepository.getQuestions(filter, pageable)
    }

    @Transactional(readOnly = true)
    fun getDetail(id: UUID): QuestionDetailResponse {
        return QuestionDetailResponse(getById(id))
    }

    private fun getById(id: UUID): Question {
        return questionRepository.findById(id)
            .orElseThrow { QuestionException.notFound() }
    }
}