package jobterview.api.question.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jobterview.api.question.response.QuestionDetailResponse
import jobterview.api.question.response.QuestionResponse
import jobterview.api.question.service.QuestionService
import jobterview.api.question.vo.QuestionFilter
import jobterview.common.response.ApiResponse
import jobterview.domain.question.enums.QuestionDifficulty
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/questions")
@Tag(name = "Question", description = "면접 질문 API")
class QuestionController(
    private val questionService: QuestionService
){

    @Operation(summary = "질문 목록 조회")
    @GetMapping
    fun getQuestions(
        @RequestParam jobId: UUID?,
        @RequestParam difficulty: String?,
        @RequestParam(defaultValue = "0") @Min(0) page: Int,
        @RequestParam(defaultValue = "10") @Min(1) @Max(100) size: Int,
    ): ApiResponse<Page<QuestionResponse>> {
        val filter =
            QuestionFilter(
                jobId = jobId,
                difficulty = difficulty?.let { QuestionDifficulty.fromCode(it) },
            )

        return ApiResponse.create(questionService.getQuestions(filter, page, size))
    }

    @Operation(summary = "질문 상세 조회")
    @GetMapping("/{id}")
    fun getDetail(@PathVariable id: UUID): ApiResponse<QuestionDetailResponse> {
        return ApiResponse.create(questionService.getDetail(id))
    }
}