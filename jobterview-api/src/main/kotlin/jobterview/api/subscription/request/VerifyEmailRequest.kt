package jobterview.api.subscription.request

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

data class VerifyEmailRequest (
    @Schema(description = "이메일 주소", example = "user@example.com")
    val email: String,

    @Schema(description = "직업 ID")
    val jobId: UUID
)