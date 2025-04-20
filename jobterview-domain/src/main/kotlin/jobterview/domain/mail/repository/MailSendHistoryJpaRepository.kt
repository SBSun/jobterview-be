package jobterview.domain.mail.repository

import jobterview.domain.mail.MailSendHistory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MailSendHistoryJpaRepository : JpaRepository<MailSendHistory, UUID> {
}