package jobterview.api.question.service

import jobterview.api.question.response.QuestionResponse
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
}