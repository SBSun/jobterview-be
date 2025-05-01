package jobterview.api.question.response

import io.swagger.v3.oas.annotations.media.Schema
import jobterview.domain.question.Question
import java.util.*

data class QuestionResponse(
    @Schema(description = "질문 ID")
    val id: UUID,

    @Schema(description = "질문 내용")
    val content: String,

    @Schema(description = "질문 난이도")
    val difficulty: String,
) {
    constructor(question: Question) : this(
        id = question.id,
        content = question.content,
        difficulty = question.difficulty.code
    )
}
