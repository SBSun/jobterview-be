package jobterview.domain.mail.exception

import jobterview.common.exception.ApiException

class MailTokenException(
    private val statusCode: Int,
    override val message: String,
) : ApiException(message) {

    override fun statusCode(): Int {
        return statusCode
    }

    companion object {
        fun invalid(): MailTokenException {
            return MailTokenException(400, "유효하지 않은 인증 정보입니다.")
        }
    }
}
