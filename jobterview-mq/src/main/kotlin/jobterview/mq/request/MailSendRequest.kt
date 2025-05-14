package jobterview.mq.request

data class MailSendRequest(
    val to: String,
    val subject: String,
    val text: String
)
