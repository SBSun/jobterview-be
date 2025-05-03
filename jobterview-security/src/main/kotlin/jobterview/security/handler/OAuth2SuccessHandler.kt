package jobterview.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jobterview.security.jwt.JwtService
import jobterview.security.principal.MemberDetails
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2SuccessHandler(
    private val jwtService: JwtService
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val memberDetails = authentication.principal as MemberDetails
        val member = memberDetails.getMember()

        val accessToken = jwtService.generateAccessToken(member)
        val refreshToken = jwtService.generateRefreshToken(member)

        // 예: 응답에 토큰 담아서 보내기
        response.contentType = "application/json"
        response.characterEncoding = "utf-8"
        response.writer.write(
            """
            {
                "accessToken": "$accessToken",
                "refreshToken": "$refreshToken"
            }
            """.trimIndent()
        )
    }
}