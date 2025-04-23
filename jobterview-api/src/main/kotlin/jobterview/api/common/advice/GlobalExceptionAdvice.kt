package jobterview.api.common.advice

import jobterview.common.exception.ApiException
import jobterview.common.response.ApiErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception) = ApiErrorResponse(
        statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        message = e.message!!,
    )

    @ExceptionHandler(ApiException::class)
    fun handleApiException(e: ApiException): ResponseEntity<ApiErrorResponse> {
        val body = ApiErrorResponse(
            statusCode = e.statusCode(),
            message = e.message!!
        )

        return ResponseEntity.status(e.statusCode()).body(body)
    }
}