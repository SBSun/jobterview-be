package jobterview.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jobterview.common.response.ApiErrorResponse
import jobterview.security.exception.SecurityException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(request: HttpServletRequest, response: HttpServletResponse, accessDeniedException: AccessDeniedException) {
        val errorResponse = ApiErrorResponse(
            statusCode = SecurityException.forbidden().statusCode(),
            message = SecurityException.forbidden().message
        )

        try {
            response.writer.print(ObjectMapper().writeValueAsString(errorResponse))
        } catch (e: Exception) {
            print(e.message)
        }
    }
}