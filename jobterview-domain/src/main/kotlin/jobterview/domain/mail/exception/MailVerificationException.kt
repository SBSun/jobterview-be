package jobterview.domain.mail.exception

import jobterview.common.exception.ApiException

class MailVerificationException(
    private val statusCode: Int,
    override val message: String,
) : ApiException(message) {

    override fun statusCode(): Int {
        return statusCode
    }

    companion object {
        fun invalid(): MailVerificationException {
            return MailVerificationException(400, "유효하지 않은 인증 정보입니다.")
        }

        fun expired(): MailVerificationException {
            return MailVerificationException(400, "만료된 인증 정보입니다.")
        }

        fun mismatch(): MailVerificationException {
            return MailVerificationException(400, "인증 코드가 일치하지 않습니다.")
        }
    }
}
