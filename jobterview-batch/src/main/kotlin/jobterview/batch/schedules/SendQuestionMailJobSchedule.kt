package jobterview.batch.schedules

import jobterview.common.utils.LogUtil
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

    @Scheduled(cron = "0 0 7 * * ?")
    fun run() {
        val jobParameters = JobParametersBuilder()
            .addLong("timestamp", System.currentTimeMillis())
            .toJobParameters()

        try {
            LogUtil.info("메일 전송 Job 시작")
            jobLauncher.run(sendQuestionMailJob, jobParameters)
            LogUtil.info("메일 전송 Job 완료")
        } catch (e: Exception) {
            LogUtil.error("메일 전송 Job 실패: ${e.message}")
        }
    }
}