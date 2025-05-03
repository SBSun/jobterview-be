package jobterview.domain.member.enums

import jakarta.persistence.Converter
import jobterview.domain.common.converter.AbstractEnumConverter

@Converter
class ProviderTypeEnumConverter : AbstractEnumConverter<ProviderType>() {
    override fun convertToEntityAttribute(dbData: String?): ProviderType {
        return toEntityAttribute(ProviderType::class.java, dbData)
    }
}