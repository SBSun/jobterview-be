package jobterview.api.subscription.response

import io.swagger.v3.oas.annotations.media.Schema
import jobterview.domain.subscription.Subscription
import java.util.*

data class SubscriptionResponse(
    @Schema(description = "구독 ID")
    val id: UUID,

    @Schema(description = "직업 정보")
    val job: JobInfo
) {
    constructor(subscription: Subscription) : this(
        id = subscription.id,
        job = JobInfo(
            id = subscription.job.id,
            position = subscription.job.position
        )
    )

    data class JobInfo(
        @Schema(description = "직업 ID")
        val id: UUID,

        @Schema(description = "포지션")
        val position: String
    )
}
