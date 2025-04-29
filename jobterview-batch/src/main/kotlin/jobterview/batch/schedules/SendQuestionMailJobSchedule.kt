package jobterview.batch.schedules

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SendQuestionMailJobSchedule(
    private val jobLauncher: JobLauncher,
    private val sendQuestionMailJob: Job
) {

//    @Scheduled(cron = "0 0 7 * * ?")
    @Scheduled(fixedDelay = 1000000)
    fun run() {
        val jobParameters = JobParametersBuilder()
            .addLong("timestamp", System.currentTimeMillis())
            .toJobParameters()

        try {
            println("메일 전송 Job 시작")
            jobLauncher.run(sendQuestionMailJob, jobParameters)
            println("메일 전송 Job 완료")
        } catch (e: Exception) {
            println("메일 전송 Job 실패: ${e.message}")
        }
    }
}