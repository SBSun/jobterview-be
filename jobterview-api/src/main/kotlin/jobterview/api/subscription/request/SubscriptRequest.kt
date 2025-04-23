package jobterview.api.subscription.request

import java.util.*

data class SubscriptRequest (
    val email: String,
    val code: String,
    val jobId: UUID
)