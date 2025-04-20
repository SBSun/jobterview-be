package jobterview.domain.mail.repository

import jobterview.domain.mail.MailVerification
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MailVerificationJpaRepository : JpaRepository<MailVerification, UUID> {

    fun findByEmail(email: String): MailVerification?
}