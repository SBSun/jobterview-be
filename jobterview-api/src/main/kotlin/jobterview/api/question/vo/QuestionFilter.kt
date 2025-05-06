package jobterview.api.question.vo

import jobterview.domain.question.enums.QuestionDifficulty
import java.util.*

data class QuestionFilter(
    val jobId: UUID? = null,
    val difficulty: QuestionDifficulty? = null,
)
