package jobterview.api.question.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jobterview.api.question.query.service.usecase.EmailQuestionQueryUseCase
import jobterview.api.question.response.QuestionResponse
import jobterview.api.question.vo.QuestionFilter
import jobterview.common.response.ApiResponse
import jobterview.domain.question.enums.QuestionDifficulty
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/email-questions")
@Tag(name = "Email Question")
class EmailQuestionController (
    private val emailQuestionQueryUseCase: EmailQuestionQueryUseCase
) {

    @Operation(summary = "이메일별 전송된 질문 목록 조회")
    @GetMapping
    fun getQuestionsByEmail(
        @RequestParam email: String,
        @RequestParam token: String,
        @RequestParam jobId: UUID?,
        @RequestParam difficulty: String?,
        @RequestParam searchKeyword: String?,
        @RequestParam(defaultValue = "0") @Min(0) page: Int,
        @RequestParam(defaultValue = "10") @Min(1) @Max(100) size: Int,
        @RequestParam(defaultValue = "createdAt") sort: String,
        @RequestParam(defaultValue = "desc") direction: String,
    ): ApiResponse<Page<QuestionResponse>> {
        val filter =
            QuestionFilter(
                jobId = jobId,
                difficulty = difficulty?.let { QuestionDifficulty.fromCode(it) },
                searchKeyword = searchKeyword
            )

        val sortDirection = Sort.Direction.fromOptionalString(direction.uppercase()).orElse(Sort.Direction.DESC)
        val pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort))

        return ApiResponse.create(emailQuestionQueryUseCase.getQuestionsByEmail(email, token, filter, pageable))
    }
}