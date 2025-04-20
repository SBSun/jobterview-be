package jobterview.domain.job.repository

import jobterview.domain.job.Job
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JobJpaRepository : JpaRepository<Job, UUID> {
}