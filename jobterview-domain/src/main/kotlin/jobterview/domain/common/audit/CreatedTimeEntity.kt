package jobterview.domain.common.audit

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@MappedSuperclass
abstract class CreatedTimeEntity : AuditingEntity() {

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    @Comment("등록일시")
    lateinit var createdAt: LocalDateTime
        protected set
}