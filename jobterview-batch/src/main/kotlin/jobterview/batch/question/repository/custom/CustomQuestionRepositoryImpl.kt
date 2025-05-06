package jobterview.batch.question.repository.custom

import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import jobterview.domain.question.QQuestion.question
import jobterview.domain.question.QSentQuestion.sentQuestion
import jobterview.domain.question.Question
import java.util.*

class CustomQuestionRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : CustomQuestionRepository {

    override fun findFirstUnsentQuestionByEmailAndJob(email: String, jobId: UUID): Question? {
        return queryFactory
            .selectFrom(question)
            .where(
                question.jobId.eq(jobId),
                question.id.notIn(
                    JPAExpressions
                        .select(sentQuestion.question().id)
                        .from(sentQuestion)
                        .where(sentQuestion.email.eq(email))
                )
            )
            .fetchFirst()
    }
}