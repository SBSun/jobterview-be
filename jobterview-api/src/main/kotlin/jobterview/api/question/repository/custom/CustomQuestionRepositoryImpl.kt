package jobterview.api.question.repository.custom

import com.querydsl.core.BooleanBuilder
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
        val builder = BooleanBuilder()
            .apply {
                filter.jobId?.let {
                    and(question.jobId.eq(it))
                }
                filter.difficulty?.let {
                    and(question.difficulty.eq(it))
                }
            }

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
            .where(builder)
            .orderBy(question.createdAt.asc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        return PageableExecutionUtils.getPage(results, pageable) {
            queryFactory
                .select(question.id.count())
                .from(question)
                .where(builder)
                .fetchOne() ?: 0L
        }
    }
}