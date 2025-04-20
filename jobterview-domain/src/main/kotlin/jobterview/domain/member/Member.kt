package jobterview.domain.member

import com.fasterxml.uuid.Generators
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jobterview.domain.common.audit.BaseTimeEntity
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

    @Column(nullable = false, unique = true)
    @Comment("이메일")
    var email: String,

    @Comment("삭제일시")
    var deletedAt: LocalDateTime? = null
): BaseTimeEntity()