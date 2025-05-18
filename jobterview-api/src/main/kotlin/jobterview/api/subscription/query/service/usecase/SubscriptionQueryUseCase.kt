package jobterview.api.subscription.query.service.usecase

import jobterview.api.subscription.response.SubscriptionResponse

interface SubscriptionQueryUseCase {
    fun getSubscriptionsByEmail(email: String, token: String): List<SubscriptionResponse>
}