package jobterview.domain.mail

import com.fasterxml.uuid.Generators
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jobterview.domain.common.audit.CreatedTimeEntity
import org.hibernate.annotations.Comment
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "mail_verification")
@Comment("메일 인증 정보")
class MailVerification (

    @Id
    @Column(updatable = false, nullable = false)
    val id: UUID = Generators.timeBasedEpochGenerator().generate(),

    @Column(nullable = false)
    @Comment("이메일")
    val email: String,

    @Column(name = "verify_code", nullable = false)
    @Comment("인증 코드")
    val verifyCode: String,

    @Column(name = "is_verified", nullable = false)
    @Comment("인증 여부")
    var isVerified: Boolean = false,

    @Column(name = "verified_at")
    @Comment("인증일시")
    var verifiedAt: LocalDateTime? = null,

    @Column(name = "expired_at", nullable = false)
    @Comment("만료일시")
    val expiredAt: LocalDateTime
): CreatedTimeEntity() {

    fun verified() {
        isVerified = true
        verifiedAt = LocalDateTime.now()
    }
}