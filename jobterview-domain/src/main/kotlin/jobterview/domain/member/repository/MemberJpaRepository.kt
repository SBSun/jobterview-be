package jobterview.domain.member.repository

import jobterview.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberJpaRepository : JpaRepository<Member, UUID> {
    fun findByEmail(email: String): Member?
}