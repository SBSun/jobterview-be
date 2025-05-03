package jobterview.security.config

import jobterview.security.filter.JwtAuthenticationFilter
import jobterview.security.handler.CustomAccessDeniedHandler
import jobterview.security.handler.CustomAuthenticationEntryPoint
import jobterview.security.handler.OAuth2SuccessHandler
import jobterview.security.oauth2.service.CustomOAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration

@Configuration
@ComponentScan(basePackages = ["jobterview.security"])
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig (
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val oAuth2SuccessHandler: OAuth2SuccessHandler,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val customAccessDeniedHandler: CustomAccessDeniedHandler,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
) {
    @Bean
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .build()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf {
                it.disable()
            }
            .cors {
                it.configurationSource { req ->
                    CorsConfiguration().apply {
                        allowedOrigins = listOf("*")
                        allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        allowedHeaders = listOf("*")
                        allowCredentials = true
                    }
                }
            }
            .httpBasic {
                it.disable()
            }
            .formLogin {
                it.disable()
            }
            .logout {
                it.disable()
            }
            .headers { headersConfigurer ->
                headersConfigurer.frameOptions {
                    it.disable()
                }
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .exceptionHandling {
                it.accessDeniedHandler(customAccessDeniedHandler)
                it.authenticationEntryPoint(customAuthenticationEntryPoint)
            }
            .authorizeHttpRequests {
                it
                    .anyRequest()
                    .permitAll()
            }
            .oauth2Login { oAuth2LoginConfigurer ->
                oAuth2LoginConfigurer.userInfoEndpoint { userInfoEndpointConfig     ->
                    userInfoEndpointConfig.userService(customOAuth2UserService)
                }
                oAuth2LoginConfigurer.successHandler(
                    oAuth2SuccessHandler,
                )
            }
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter::class.java,
            )
        return http.build()
    }
}
