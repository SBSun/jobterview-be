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
            return SubscriptionException(statusCode = 404, message = "구독 정보가 존재하지 않습니다.")
        }
        fun alreadySubscribed(): SubscriptionException {
            return SubscriptionException(statusCode = 400, message = "이미 해당 직업에 대해 구독 중입니다.")
        }
    }
}