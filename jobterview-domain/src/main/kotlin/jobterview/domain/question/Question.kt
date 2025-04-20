package jobterview.domain.question

import com.fasterxml.uuid.Generators
import jakarta.persistence.*
import jobterview.domain.common.audit.CreatedTimeEntity
import jobterview.domain.job.Job
import org.hibernate.annotations.Comment
import java.util.*

@Entity
@Table(name = "question")
@Comment("질문")
class Question (
    @Id
    @Column(updatable = false, nullable = false)
    val id: UUID = Generators.timeBasedEpochGenerator().generate(),

    @Column(nullable = false)
    @Comment("질문")
    var content: String,

    @Column(name = "answer_example")
    @Comment("답변 예시")
    var answerExample: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    val job: Job,
): CreatedTimeEntity()