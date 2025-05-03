package jobterview.security.exception

import jobterview.common.exception.ApiException

class SecurityException (
    private val statusCode: Int,
    override val message: String,
) : ApiException(message) {

    override fun statusCode(): Int {
        return statusCode
    }

    companion object {
        fun unauthorized(): SecurityException {
            return SecurityException(401, "유효하지 않은 인증 토큰입니다.")
        }

        fun forbidden(): SecurityException {
            return SecurityException(403, "권한이 없어 요청이 거부되었습니다.")
        }
    }
}