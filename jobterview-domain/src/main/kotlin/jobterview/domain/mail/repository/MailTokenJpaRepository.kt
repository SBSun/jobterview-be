package jobterview.domain.mail.repository

import jobterview.domain.mail.MailToken
import org.springframework.data.jpa.repository.JpaRepository

interface MailTokenJpaRepository : JpaRepository<MailToken, String> {
}