package jobterview.batch.question.repository

import jobterview.domain.question.SentQuestion
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SentQuestionRepository : JpaRepository<SentQuestion, UUID> {
}