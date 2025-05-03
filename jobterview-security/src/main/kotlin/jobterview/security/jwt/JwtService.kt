package jobterview.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import jobterview.domain.member.Member
import jobterview.security.principal.MemberDetails
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtService (
    @Value("\${jwt.secret}")
    private var secret: String,
    @Value("\${jwt.access-token-expiration-period}")
    private var accessTokenExpirationPeriod: Long,
    @Value("\${jwt.refresh-token-expiration-period}")
    private var refreshTokenExpirationPeriod: Long
) {

    @PostConstruct
    private fun getSecretKey(): SecretKey {
        if (secret.isEmpty()) {
            throw IllegalArgumentException("JWT secret key must not be empty")
        }

        return Keys.hmacShaKeyFor(secret.toByteArray())
    }

    fun generateAccessToken(member: Member): String {
        return generateToken(MemberDetails(member), accessTokenExpirationPeriod)
    }

    fun generateRefreshToken(member: Member): String {
        return generateToken(MemberDetails(member), refreshTokenExpirationPeriod)
    }

    fun isTokenExpired(token: String): Boolean {
        return extractClaim(token, Claims::getExpiration).before(Date())
    }

    fun getAuthentication(token: String): Authentication {
        val claims = extractAllClaims(token)

        val authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))

        val principal = User(
            claims.subject,
            "",
            authorities,
        )
        return UsernamePasswordAuthenticationToken(
            principal,
            token,
            authorities,
        )
    }

    private fun generateToken(memberDetails: MemberDetails, expirePeriod: Long): String {
        val now = Date()

        return Jwts.builder()
            .subject(memberDetails.username)
            .claim("role", "ROLE_USER")
            .issuedAt(now)
            .expiration(Date(now.time + expirePeriod))
            .signWith(getSecretKey(), Jwts.SIG.HS512)
            .compact()
    }

    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(getSecretKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }
}