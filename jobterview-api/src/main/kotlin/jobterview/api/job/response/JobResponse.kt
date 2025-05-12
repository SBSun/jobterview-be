package jobterview.api.job.response

import io.swagger.v3.oas.annotations.media.Schema
import jobterview.domain.job.Job
import java.util.*

data class JobResponse(
    @Schema(description = "직업 ID")
    val id: UUID = UUID(0, 0),

    @Schema(description = "포지션")
    val position: String = ""
) {
    constructor(job: Job) : this(
        id = job.id,
        position = job.position
    )
}
