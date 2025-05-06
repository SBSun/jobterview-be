package jobterview.api.question.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jobterview.api.question.response.QuestionDetailResponse
import jobterview.api.question.response.QuestionResponse
import jobterview.api.question.service.QuestionService
import jobterview.common.response.ApiResponse
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/questions")
@Tag(name = "Question", description = "면접 질문 API")
class QuestionController(
    private val questionService: QuestionService
){

    @Operation(summary = "직업별 질문 조회")
    @GetMapping
    fun getQuestionsByJob(@RequestParam jobId: UUID): ApiResponse<List<QuestionResponse>> {
        return ApiResponse.create(questionService.getQuestionsByJob(jobId))
    }

    @Operation(summary = "질문 상세 조회")
    @GetMapping("/{id}")
    fun getDetail(@PathVariable id: UUID): ApiResponse<QuestionDetailResponse> {
        return ApiResponse.create(questionService.getDetail(id))
    }
}