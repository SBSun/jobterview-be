package jobterview.api.subscription.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jobterview.api.subscription.request.SubscriptRequest
import jobterview.api.subscription.request.VerifyEmailRequest
import jobterview.api.subscription.response.SubscriptionResponse
import jobterview.api.subscription.service.SubscriptionService
import jobterview.common.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/subscriptions")
@Tag(name = "Subscription", description = "구독 API")
class SubscriptionController (
    private val subscriptionService: SubscriptionService
){

    @Operation(summary = "구독 인증 메일 발송")
    @PostMapping("/verify/send")
    fun sendVerifyEmail(@RequestBody request: VerifyEmailRequest) {
        subscriptionService.sendVerifyEmail(request)
    }

    @Operation(summary = "구독 요청")
    @PostMapping
    fun subscript(@RequestBody request: SubscriptRequest) {
        subscriptionService.subscript(request)
    }

    @Operation(summary = "이메일별 구독 정보 목록 조회")
    @GetMapping
    fun getSubscriptionsByEmail(
        @RequestParam email: String,
        @RequestParam token: String
    ): ApiResponse<List<SubscriptionResponse>> {
        return ApiResponse.create(subscriptionService.getSubscriptions(email, token))
    }
}