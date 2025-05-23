package jobterview.api.question.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jobterview.api.question.query.service.usecase.QuestionQueryUseCase
import jobterview.api.question.response.QuestionDetailResponse
import jobterview.api.question.response.QuestionResponse
import jobterview.api.question.vo.QuestionFilter
import jobterview.common.response.ApiResponse
import jobterview.domain.question.enums.QuestionDifficulty
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/questions")
@Tag(name = "Question", description = "면접 질문 API")
class QuestionController(
    private val questionQueryUseCase: QuestionQueryUseCase
){

    @Operation(summary = "질문 목록 조회")
    @GetMapping
    fun getQuestions(
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

        return ApiResponse.create(questionQueryUseCase.getQuestions(filter, pageable))
    }

    @Operation(summary = "질문 조회")
    @GetMapping("/{id}")
    fun getDetail(@PathVariable id: UUID): ApiResponse<QuestionDetailResponse> {
        return ApiResponse.create(questionQueryUseCase.getDetail(id))
    }
}