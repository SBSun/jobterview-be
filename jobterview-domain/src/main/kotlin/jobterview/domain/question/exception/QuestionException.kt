package jobterview.domain.question.exception

import jobterview.common.exception.ApiException

class QuestionException(
    private val statusCode: Int,
    override val message: String,
) : ApiException(message) {

    override fun statusCode(): Int {
        return statusCode
    }

    companion object {
        fun notFound(): QuestionException {
            return QuestionException(statusCode = 404, message = "질문이 존재하지 않습니다.")
        }
    }
}