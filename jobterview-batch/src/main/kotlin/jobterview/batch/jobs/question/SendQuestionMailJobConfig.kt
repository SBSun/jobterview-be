package jobterview.batch.jobs.question

import jobterview.batch.question.repository.QuestionRepository
import jobterview.batch.question.repository.SentQuestionRepository
import jobterview.domain.question.SentQuestion
import jobterview.domain.subscription.Subscription
import jobterview.domain.subscription.repository.SubscriptionJpaRepository
import jobterview.mail.MailSender
import jobterview.mail.template.QuestionMailTemplate
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class SendQuestionMailJobConfig (
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val mailSender: MailSender,
    private val questionMailTemplate: QuestionMailTemplate,
    private val subscriptionRepository: SubscriptionJpaRepository,
    private val questionRepository: QuestionRepository,
    private val sentQuestionRepository: SentQuestionRepository
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
            .chunk<Subscription, Subscription>(10, transactionManager)
            .reader(subscriptionReader())
            .writer { subscriptions ->
                subscriptions.forEach { subscription ->
                    val job = subscription.job
                    val question = questionRepository.findFirstUnsentQuestionByEmailAndJob(subscription.email, job.id)

                    if (question != null) {
                        val to = subscription.email
                        val subject = "오늘의 면접 질문"
                        val text = questionMailTemplate.render(mapOf("question" to question.content))

                        mailSender.sendMail(to, subject, text)

                        val sentQuestion = SentQuestion(
                            email = subscription.email,
                            question = question,
                            questionId = question.id,
                            subscription = subscription
                        )
                        sentQuestionRepository.save(sentQuestion)
                    } else {
                        println("질문 없음: jobId=${job.id}")
                    }
                }
            }
            .build()
    }

    @Bean
    fun subscriptionReader(): ItemReader<Subscription> {
        val subscriptions = subscriptionRepository.findAll()
        val iterator = subscriptions.iterator()

        return object : ItemReader<Subscription> {
            override fun read(): Subscription? {
                if (iterator.hasNext()) {
                    return iterator.next()
                }
                return null
            }
        }
    }
}