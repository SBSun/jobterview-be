package jobterview.common.exception

abstract class ApiException(message: String) : RuntimeException(message) {
    abstract fun statusCode(): Int
}