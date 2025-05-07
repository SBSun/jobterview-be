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
import jobterview.domain.question.QSentQuestion.sentQuestion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.support.PageableExecutionUtils

class CustomQuestionRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : CustomQuestionRepository {

    override fun getQuestions(filter: QuestionFilter, pageable: Pageable): Page<QuestionResponse> {
        val conditionBuilder = createConditionBuilder(filter)
        val orderSpecifiers = createOrderSpecifiers(pageable.sort)

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

    override fun getQuestionsByEmail(email: String, filter: QuestionFilter, pageable: Pageable): Page<QuestionResponse> {
        val conditionBuilder = createConditionBuilder(filter)
        val orderSpecifiers = createOrderSpecifiers(pageable.sort)

        val results = queryFactory
            .select(
                Projections.constructor(
                    QuestionResponse::class.java,
                    question,
                    job
                )
            )
            .from(question)
            .innerJoin(job).on(question.jobId.eq(job.id))
            .innerJoin(sentQuestion).on(
                sentQuestion.email.eq(email)
                    .and(sentQuestion.questionId.eq(question.id))
            )
            .where(conditionBuilder)
            .orderBy(*orderSpecifiers)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        return PageableExecutionUtils.getPage(results, pageable) {
            queryFactory
                .select(question.count())
                .from(question)
                .innerJoin(job).on(question.jobId.eq(job.id))
                .innerJoin(sentQuestion).on(
                    sentQuestion.email.eq(email)
                        .and(sentQuestion.questionId.eq(question.id))
                )
                .where(conditionBuilder)
                .fetchOne() ?: 0L
        }
    }

    private fun createConditionBuilder(filter: QuestionFilter): BooleanBuilder {
        return BooleanBuilder()
            .apply {
                filter.jobId?.let {
                    and(question.jobId.eq(it))
                }
                filter.difficulty?.let {
                    and(question.difficulty.eq(it))
                }
                filter.searchKeyword?.let {
                    and(question.content.containsIgnoreCase(it))
                }
            }
    }

    fun createOrderSpecifiers(sort: Sort): Array<OrderSpecifier<*>> {
        return sort.mapNotNull { order ->
            val direction = if (order.isAscending) Order.ASC else Order.DESC

            when (order.property) {
                "createdAt" -> OrderSpecifier(direction, question.createdAt)
                "content" -> OrderSpecifier(direction, question.content)
                "difficulty" -> OrderSpecifier(direction, question.difficulty)
                "position" -> OrderSpecifier(direction, job.position)
                else -> null
            }
        }.toTypedArray()
    }
}