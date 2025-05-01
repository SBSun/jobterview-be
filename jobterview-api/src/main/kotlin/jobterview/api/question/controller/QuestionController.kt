package jobterview.api.question.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jobterview.api.question.response.QuestionResponse
import jobterview.api.question.service.QuestionService
import jobterview.common.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/questions")
@Tag(name = "Question", description = "면접 질문 API")
class QuestionController(
    private val questionService: QuestionService
){

    @Operation(summary = "면접 질문 조회")
    @GetMapping
    fun sendVerifyEmail(@RequestParam jobId: UUID): ApiResponse<List<QuestionResponse>> {
        return ApiResponse.create(questionService.getQuestionsByJob(jobId))
    }
}