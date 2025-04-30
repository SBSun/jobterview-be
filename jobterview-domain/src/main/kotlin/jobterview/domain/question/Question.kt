package jobterview.domain.question

import com.fasterxml.uuid.Generators
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jobterview.domain.common.audit.CreatedTimeEntity
import jobterview.domain.job.Job
import jobterview.domain.question.enums.QuestionDifficulty
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val difficulty: QuestionDifficulty,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    val job: Job,
): CreatedTimeEntity()