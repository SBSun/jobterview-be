package jobterview.domain.question

import com.fasterxml.uuid.Generators
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jobterview.domain.common.audit.CreatedTimeEntity
import jobterview.domain.job.Job
import jobterview.domain.subscription.Subscription
import org.hibernate.annotations.Comment
import java.util.*

@Entity
@Table(name = "sent_question")
@Comment("질문 이메일 발송 이력")
class SentQuestion (
    @Id
    @Column(updatable = false, nullable = false)
    val id: UUID = Generators.timeBasedEpochGenerator().generate(),

    @Column(nullable = false)
    @Comment("이메일")
    var email: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    val question: Question,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id")
    val subscription: Subscription,
): CreatedTimeEntity()