package jobterview.domain.common.audit

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseTimeEntity : AuditingEntity() {
    @CreatedDate
    @Column(name = "created_at")
    @Comment("등록일시")
    lateinit var createdAt: LocalDateTime
        protected set

    @LastModifiedDate
    @Column(name = "updated_at")
    @Comment("수정일시")
    lateinit var updatedAt: LocalDateTime
        protected set
}