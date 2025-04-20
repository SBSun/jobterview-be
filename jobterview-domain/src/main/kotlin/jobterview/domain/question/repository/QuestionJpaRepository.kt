package jobterview.domain.question.repository

import jobterview.domain.question.Question
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface QuestionJpaRepository : JpaRepository<Question, UUID> {
}