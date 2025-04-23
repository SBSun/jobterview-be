package jobterview.domain.mail.repository

import jobterview.domain.mail.MailVerification
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MailVerificationJpaRepository : JpaRepository<MailVerification, UUID> {

    fun findTopByEmailOrderByCreatedAtDesc(email: String): Optional<MailVerification>
}