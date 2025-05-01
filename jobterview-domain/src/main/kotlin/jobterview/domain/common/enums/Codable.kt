package jobterview.domain.common.enums

interface Codable {
    val code: String

    companion object {
        fun <E> fromCode(cls: Class<E>, code: String?): E where E : Enum<E>, E : Codable {
            if (code.isNullOrBlank()) {
                throw IllegalArgumentException("code는 null이거나 빈 값일 수 없습니다.")
            }

            val trimmed = code.trim()
            return cls.enumConstants
                .firstOrNull { (it as Codable).code.equals(trimmed, ignoreCase = true) }
                ?: throw IllegalArgumentException("${cls.name}에 $code 가 존재하지 않습니다.")
        }
    }
}