package jobterview.batch.jobs.question

import jobterview.domain.question.repository.QuestionJpaRepository
import jobterview.domain.subscription.Subscription
import jobterview.domain.subscription.repository.SubscriptionJpaRepository
import jobterview.mail.MailSender
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemReader
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class SendQuestionMailJobConfig (
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val mailSender: MailSender,
    private val subscriptionRepository: SubscriptionJpaRepository,
    private val questionRepository: QuestionJpaRepository
){

    @Bean
    fun sendMailJob(): Job {
        return JobBuilder("sendMailJob", jobRepository)
            .start(sendMailStep())
            .build()
    }

    @Bean
    fun sendMailStep(): Step {
        return StepBuilder("sendMailStep", jobRepository)
            .tasklet({ contribution, chunkContext ->
                val subscription = subscriptionReader().read()

                if (subscription == null) {
                    println("구독 정보 없음")
                    return@tasklet RepeatStatus.FINISHED
                }

                val job = subscription.job
                val question = questionRepository.findFirstByJobIdOrderByCreatedAtDesc(job.id)

                if (question == null) {
                    println("질문 없음: jobId=${job.id}")
                    return@tasklet RepeatStatus.FINISHED
                }

                val to = subscription.email
                val subject = "오늘의 면접 질문"
                val text = "면접 질문 내용: ${question.content}"

                mailSender.sendMail(to, subject, text)

                return@tasklet RepeatStatus.FINISHED
            }, transactionManager)
            .build()
    }

    @Bean
    fun subscriptionReader(): ItemReader<Subscription> {
        return object : ItemReader<Subscription> {
            override fun read(): Subscription? {
                return subscriptionRepository.findFirstByOrderByCreatedAtDesc()
            }
        }
    }
}