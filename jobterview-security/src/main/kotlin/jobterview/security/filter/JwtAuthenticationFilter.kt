package jobterview.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jobterview.security.jwt.JwtService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val accessToken = resolveAccessToken(request)

        try {
            if (accessToken != null && !jwtService.isTokenExpired(accessToken)) {
                val authentication = jwtService.getAuthentication(accessToken)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            SecurityContextHolder.clearContext()

            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = "application/json"
            response.writer.write(
                """{"error": "Invalid token"}"""
            )
            response.writer.flush()
        }

        filterChain.doFilter(request, response)
    }

    private fun resolveAccessToken(request: HttpServletRequest): String? {
        val authorizationHeader = request.getHeader("Authorization")

        return if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            authorizationHeader.substring(7)
        } else {
            null
        }
    }
}