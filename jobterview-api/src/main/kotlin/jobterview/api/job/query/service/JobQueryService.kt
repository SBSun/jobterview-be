package jobterview.api.job.query.service

import jobterview.api.job.query.service.usecase.JobQueryUseCase
import jobterview.api.job.response.JobResponse
import jobterview.domain.job.Job
import jobterview.domain.job.exception.JobException
import jobterview.domain.job.repository.JobJpaRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class JobQueryService (
    private val jobRepository: JobJpaRepository
) : JobQueryUseCase {

    /**
     * 직업 ID로 직업 엔티티를 조회합니다.
     *
     * @param id 직업 ID
     * @return 직업 엔티티
     */
    override fun getById(id: UUID): Job {
        return jobRepository.findById(id)
            .orElseThrow { JobException.notFound() }
    }

    /**
     * 모든 직업 목록을 조회합니다.
     *
     * @return 직업 정보 목록
     */
    @Cacheable(cacheNames = ["jobs"], key = "'jobs'")
    override fun getJobs(): List<JobResponse> {
        return jobRepository.findAll()
            .map { JobResponse(it) }
            .sortedBy { it.position }
    }

    /**
     * 직업 존재여부를 검증합니다.
     */
    override fun verifyExist(id: UUID) {
        if (!jobRepository.existsById(id)) {
            throw JobException.notFound()
        }
    }
}