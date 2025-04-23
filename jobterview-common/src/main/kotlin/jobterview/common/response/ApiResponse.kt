package jobterview.common.response

data class ApiResponse<T>(
    val data: T,
) {

    companion object {
        fun <T> create(data: T) = ApiResponse(data)
    }
}