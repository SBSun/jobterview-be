package jobterview.mail.template

interface MailTemplate {

    fun render(data: Map<String, Any>): String
}