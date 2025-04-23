package jobterview.api.mail.template

interface MailTemplate {

    fun render(data: Map<String, Any>): String
}