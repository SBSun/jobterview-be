package jobterview.domain.job

import com.fasterxml.uuid.Generators
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jobterview.domain.common.audit.BaseTimeEntity
import org.hibernate.annotations.Comment
import java.util.*

@Entity
@Table(name = "job")
@Comment("직업")
class Job (
    @Id
    @Column(updatable = false, nullable = false)
    val id: UUID = Generators.timeBasedEpochGenerator().generate(),

    @Column(nullable = false)
    @Comment("포지션")
    var position: String,

    @Column(nullable = false)
    @Comment("직업 설명")
    var description: String,
): BaseTimeEntity()