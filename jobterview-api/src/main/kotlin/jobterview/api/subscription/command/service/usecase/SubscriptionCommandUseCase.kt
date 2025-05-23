package jobterview.api.subscription.command.service.usecase

import jobterview.api.subscription.request.SubscriptRequest
import jobterview.api.subscription.request.VerifyEmailRequest
import java.util.*

interface SubscriptionCommandUseCase {
    fun sendVerifyEmail(request: VerifyEmailRequest)
    fun subscript(request: SubscriptRequest)
    fun unsubscribe(id: UUID, email: String, token: String)
}