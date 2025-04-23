package jobterview.api.job.service

import jobterview.domain.job.Job
import jobterview.domain.job.exception.JobException
import jobterview.domain.job.repository.JobJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class JobService (
    private val jobRepository: JobJpaRepository
){

    fun getById(id: UUID): Job {
        return jobRepository.findById(id)
            .orElseThrow { JobException.notFound() }
    }
}