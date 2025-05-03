package jobterview.domain.member.enums

import jobterview.domain.common.enums.Codable

enum class ProviderType(override val code: String) : Codable {
    GOOGLE("google"),
    KAKAO("kakao"),
    NAVER("naver");

    companion object {
        fun fromCode(code: String): ProviderType {
            return Codable.fromCode(ProviderType::class.java, code)
        }
    }
}