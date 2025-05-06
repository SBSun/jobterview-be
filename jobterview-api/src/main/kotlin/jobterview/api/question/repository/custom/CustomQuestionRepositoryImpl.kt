package jobterview.api.question.repository.custom

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import jobterview.api.question.response.QuestionResponse
import jobterview.api.question.vo.QuestionFilter
import jobterview.domain.job.QJob.job
import jobterview.domain.question.QQuestion.question
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils

class CustomQuestionRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : CustomQuestionRepository {

    override fun getQuestions(filter: QuestionFilter, pageable: Pageable): Page<QuestionResponse> {
        val conditionBuilder = BooleanBuilder()
            .apply {
                filter.jobId?.let {
                    and(question.jobId.eq(it))
                }
                filter.difficulty?.let {
                    and(question.difficulty.eq(it))
                }
            }

        val orderSpecifiers = pageable.sort.mapNotNull { order ->
            val direction = if (order.isAscending) Order.ASC else Order.DESC

            when (order.property) {
                "createdAt" -> OrderSpecifier(direction, question.createdAt)
                "content" -> OrderSpecifier(direction, question.content)
                "difficulty" -> OrderSpecifier(direction, question.difficulty)
                "position" -> OrderSpecifier(direction, job.position)
                else -> null
            }
        }.toTypedArray()

        val results = queryFactory
            .select(
                Projections.constructor(
                    QuestionResponse::class.java,
                    question,
                    job
                )
            )
            .from(question)
            .join(job)
            .on(question.jobId.eq(job.id))
            .where(conditionBuilder)
            .orderBy(*orderSpecifiers)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        return PageableExecutionUtils.getPage(results, pageable) {
            queryFactory
                .select(question.id.count())
                .from(question)
                .where(conditionBuilder)
                .fetchOne() ?: 0L
        }
    }
}