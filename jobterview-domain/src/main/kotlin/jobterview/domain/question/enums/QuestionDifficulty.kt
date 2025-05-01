package jobterview.domain.question.enums

import jobterview.domain.common.enums.Codable

enum class QuestionDifficulty(override val code: String): Codable {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard")
}