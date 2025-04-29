package jobterview.mail.template

import org.springframework.stereotype.Component
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine

@Component
class QuestionMailTemplate (
    private val templateEngine: SpringTemplateEngine
) : MailTemplate {

    override fun render(data: Map<String, Any>): String {
        val context = Context().apply {
            setVariables(data)
        }
        return templateEngine.process("question-mail", context)
    }
}