package jobterview.domain.job.exception

import jobterview.common.exception.ApiException

class JobException(
    private val statusCode: Int,
    override val message: String,
) : ApiException(message) {

    override fun statusCode(): Int {
        return statusCode
    }

    companion object {
        fun notFound(): JobException {
            return JobException(statusCode = 404, message = "직업이 존재하지 않습니다.")
        }
    }
}