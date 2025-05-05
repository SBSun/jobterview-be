package jobterview.api.job.service

import jobterview.api.job.response.JobResponse
import jobterview.domain.job.Job
import jobterview.domain.job.exception.JobException
import jobterview.domain.job.repository.JobJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class JobService (
    private val jobRepository: JobJpaRepository
){

    @Transactional(readOnly = true)
    fun getById(id: UUID): Job {
        return jobRepository.findById(id)
            .orElseThrow { JobException.notFound() }
    }

    @Transactional(readOnly = true)
    fun getJobs(): List<JobResponse> {
        return jobRepository.findAll()
            .map { JobResponse(it) }
            .sortedBy { it.position }
    }
}