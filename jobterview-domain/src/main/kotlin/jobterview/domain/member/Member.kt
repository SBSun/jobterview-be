package jobterview.domain.member

import com.fasterxml.uuid.Generators
import jakarta.persistence.*
import jobterview.domain.common.audit.BaseTimeEntity
import jobterview.domain.member.enums.ProviderType
import jobterview.domain.member.enums.ProviderTypeEnumConverter
import org.hibernate.annotations.Comment
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "member")
@Comment("사용자")
class Member (

    @Id
    @Column(updatable = false, nullable = false)
    val id: UUID = Generators.timeBasedEpochGenerator().generate(),

    @Convert(converter = ProviderTypeEnumConverter::class)
    @Column(name = "provider", nullable = false)
    val provider: ProviderType,

    @Column(nullable = false, unique = true)
    var providerId: String,

    @Column(nullable = false, unique = true)
    @Comment("이메일")
    var email: String,

    @Comment("삭제일시")
    var deletedAt: LocalDateTime? = null
): BaseTimeEntity()