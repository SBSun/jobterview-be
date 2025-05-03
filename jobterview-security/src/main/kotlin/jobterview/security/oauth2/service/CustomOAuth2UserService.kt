package jobterview.security.oauth2.service

import jobterview.domain.member.Member
import jobterview.domain.member.enums.ProviderType
import jobterview.domain.member.repository.MemberJpaRepository
import jobterview.security.oauth2.domain.OAuth2UserInfo
import jobterview.security.principal.MemberDetails
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    private val memberRepository: MemberJpaRepository
): DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)

        val providerType = ProviderType.fromCode(userRequest.clientRegistration.registrationId)
        val oAuth2UserInfo = OAuth2UserInfo.of(providerType, oAuth2User.attributes)

        val member = memberRepository.findByEmail(oAuth2UserInfo.email)
            ?: memberRepository.save(
                Member(
                    provider = providerType,
                    providerId = oAuth2UserInfo.id,
                    email = oAuth2UserInfo.email
                )
            )

        return MemberDetails(
            member = member
        )
    }
}
