package jobterview.domain.common.converter

import jakarta.persistence.AttributeConverter
import jobterview.domain.common.enums.Codable

abstract class AbstractEnumConverter<E> : AttributeConverter<E, String> where E : Enum<E>, E : Codable {

    override fun convertToDatabaseColumn(attribute: E): String {
        return attribute.code
    }

    override fun convertToEntityAttribute(dbData: String?): E? {
        return null
    }

    fun toEntityAttribute(cls: Class<E>, code: String?): E {
        return Codable.fromCode(cls, code)
    }
}