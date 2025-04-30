package jobterview.batch.question.repository

import jobterview.batch.question.repository.custom.CustomQuestionRepository
import jobterview.domain.question.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

interface QuestionRepository : JpaRepository<Question, UUID>, CustomQuestionRepository {
}