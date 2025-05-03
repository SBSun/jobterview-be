package jobterview.security.oauth2.domain

import jobterview.domain.member.enums.ProviderType

data class OAuth2UserInfo(
    val id: String,
    val email: String,
    val provider: ProviderType
) {
    companion object {
        fun of(provider: ProviderType, attributes: Map<String, Any>): OAuth2UserInfo {
            return when (provider) {
                ProviderType.GOOGLE -> ofGoogle(attributes)
                ProviderType.KAKAO -> ofKakao(attributes)
                ProviderType.NAVER -> ofNaver(attributes)
                else -> throw RuntimeException("Unsupported provider: ${provider.code}")
            }
        }

        private fun ofGoogle(attributes: Map<String, Any>): OAuth2UserInfo {
            val id = attributes["sub"] as String
            val email = attributes["email"] as String
            return OAuth2UserInfo(
                id = "google_$id",
                email = email,
                provider = ProviderType.GOOGLE
            )
        }

        private fun ofKakao(attributes: Map<String, Any>): OAuth2UserInfo {
            val id = attributes["id"].toString()
            val email = attributes["kakao_account"].let { account ->
                (account as Map<*, *>)["email"]
            } as String

            return OAuth2UserInfo(
                id = "kakao_$id",
                provider = ProviderType.KAKAO,
                email = email
            )
        }

        private fun ofNaver(attributes: Map<String, Any>): OAuth2UserInfo {
            val response = attributes["response"] as Map<*, *>
            val id = response["id"] as String
            val email = response["email"] as String

            return OAuth2UserInfo(
                id = "naver_$id",
                provider = ProviderType.NAVER,
                email = email
            )
        }
    }
}