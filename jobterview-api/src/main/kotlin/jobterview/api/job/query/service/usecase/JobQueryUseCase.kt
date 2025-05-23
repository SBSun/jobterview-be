package jobterview.api.job.query.service.usecase

import jobterview.api.job.response.JobResponse
import jobterview.domain.job.Job
import java.util.*

interface JobQueryUseCase {
    fun getById(id: UUID): Job
    fun getJobs(): List<JobResponse>
    fun verifyExist(id: UUID)
}