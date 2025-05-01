package jobterview.domain.question.enums

import jakarta.persistence.Converter
import jobterview.domain.common.converter.AbstractEnumConverter

@Converter
class QuestionDifficultyEnumConverter : AbstractEnumConverter<QuestionDifficulty>() {
    override fun convertToEntityAttribute(dbData: String?): QuestionDifficulty {
        return toEntityAttribute(QuestionDifficulty::class.java, dbData)
    }
}