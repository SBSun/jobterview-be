package jobterview.domain.question.repository

import jobterview.domain.question.Question
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface QuestionJpaRepository : JpaRepository<Question, UUID> {

    fun findAllByJob_Id(jobId: UUID): List<Question>
}