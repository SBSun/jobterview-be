package jobterview.domain.subscription.exception

import jobterview.common.exception.ApiException

class SubscriptionException(
    private val statusCode: Int,
    override val message: String,
) : ApiException(message) {

    override fun statusCode(): Int {
        return statusCode
    }

    companion object {
        fun notFound(): SubscriptionException {
            return SubscriptionException(statusCode = 404, message = "직업이 존재하지 않습니다.")
        }
    }
}