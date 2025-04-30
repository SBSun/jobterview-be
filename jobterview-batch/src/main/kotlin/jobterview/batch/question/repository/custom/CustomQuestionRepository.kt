package jobterview.batch.question.repository.custom

import jobterview.domain.question.Question
import java.util.*

interface CustomQuestionRepository {

    fun findFirstUnsentQuestionByEmailAndJob(email: String, jobId: UUID): Question?
}