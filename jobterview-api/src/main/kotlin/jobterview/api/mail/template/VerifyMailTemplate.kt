package jobterview.api.mail.template

import org.springframework.stereotype.Component
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine


@Component
class VerifyMailTemplate(
    private val templateEngine: SpringTemplateEngine
) : MailTemplate {

    override fun render(data: Map<String, Any>): String {
        val context = Context().apply {
            setVariables(data)
        }
        return templateEngine.process("verify-mail", context)
    }
}