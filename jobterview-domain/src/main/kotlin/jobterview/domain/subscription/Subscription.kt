package jobterview.domain.subscription

import com.fasterxml.uuid.Generators
import jakarta.persistence.*
import jobterview.domain.common.audit.CreatedTimeEntity
import jobterview.domain.job.Job
import org.hibernate.annotations.Comment
import java.util.*

@Entity
@Table(name = "subscription")
@Comment("구독")
class Subscription (
    @Id
    @Column(updatable = false, nullable = false)
    val id: UUID = Generators.timeBasedEpochGenerator().generate(),

    @Column(nullable = false)
    @Comment("이메일")
    var email: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    val job: Job
): CreatedTimeEntity()