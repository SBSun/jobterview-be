package jobterview.api.job.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jobterview.api.job.query.service.usecase.JobQueryUseCase
import jobterview.api.job.response.JobResponse
import jobterview.common.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/jobs")
@Tag(name = "Job", description = "직업 API")
class JobController(
    private val jobQueryUseCase: JobQueryUseCase
) {

    @Operation(summary = "직업 목록 조회")
    @GetMapping
    fun getJobs(): ApiResponse<List<JobResponse>> {
        return ApiResponse.create(jobQueryUseCase.getJobs())
    }
}