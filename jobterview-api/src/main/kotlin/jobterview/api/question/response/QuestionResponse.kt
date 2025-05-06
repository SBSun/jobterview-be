package jobterview.api.question.response

import io.swagger.v3.oas.annotations.media.Schema
import jobterview.domain.job.Job
import jobterview.domain.question.Question
import java.util.*

data class QuestionResponse(
    @Schema(description = "질문 ID")
    val id: UUID,

    @Schema(description = "질문 내용")
    val content: String,

    @Schema(description = "질문 난이도")
    val difficulty: String,

    @Schema(description = "직업 정보")
    val job: JobInfo
) {
    constructor(question: Question, job: Job) : this(
        id = question.id,
        content = question.content,
        difficulty = question.difficulty.code,
        job = JobInfo(
            id = job.id,
            position = job.position
        )
    )

    data class JobInfo(
        @Schema(description = "직업 ID")
        val id: UUID,

        @Schema(description = "포지션")
        val position: String
    )
}
