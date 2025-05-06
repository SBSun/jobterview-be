package jobterview.api.question.service

import jobterview.api.question.response.QuestionDetailResponse
import jobterview.api.question.response.QuestionResponse
import jobterview.domain.question.Question
import jobterview.domain.question.exception.QuestionException
import jobterview.domain.question.repository.QuestionJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class QuestionService (
    private val questionRepository: QuestionJpaRepository
){

    @Transactional(readOnly = true)
    fun getQuestionsByJob(jobId: UUID): List<QuestionResponse> {
        return questionRepository.findAllByJob_Id(jobId)
            .map { QuestionResponse(it) }
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