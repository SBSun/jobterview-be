package jobterview.security.principal

import jobterview.domain.member.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

class MemberDetails(
    private val member: Member
) : UserDetails, OAuth2User {


    override fun getUsername(): String {
        return member.id.toString()
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getName(): String {
        return member.email
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return mutableMapOf(
            "id" to member.id,
            "providerType" to member.provider,
            "email" to member.email,
        )
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun getMember(): Member {
        return member
    }
}