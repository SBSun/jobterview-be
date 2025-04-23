package jobterview.domain.mail

import com.fasterxml.uuid.Generators
import jakarta.persistence.*
import jobterview.domain.common.audit.CreatedTimeEntity
import jobterview.domain.job.Job
import jobterview.domain.question.Question
import org.hibernate.annotations.Comment
import java.util.*

@Entity
@Table(name = "mail_send_history")
@Comment("메일 발송 이력")
class MailSendHistory (

    @Id
    @Column(updatable = false, nullable = false)
    val id: UUID = Generators.timeBasedEpochGenerator().generate(),

    @Column(nullable = false)
    @Comment("이메일")
    var email: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    val job: Job,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    val question: Question
): CreatedTimeEntity()