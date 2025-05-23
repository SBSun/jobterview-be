package jobterview.api.subscription.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jobterview.api.subscription.command.service.usecase.SubscriptionCommandUseCase
import jobterview.api.subscription.query.service.usecase.SubscriptionQueryUseCase
import jobterview.api.subscription.request.SubscriptRequest
import jobterview.api.subscription.request.VerifyEmailRequest
import jobterview.api.subscription.response.SubscriptionResponse
import jobterview.common.response.ApiResponse
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/subscriptions")
@Tag(name = "Subscription", description = "구독 API")
class SubscriptionController (
    private val subscriptionCommandUseCase: SubscriptionCommandUseCase,
    private val subscriptionQueryUseCase: SubscriptionQueryUseCase,
){

    @Operation(summary = "구독 인증 메일 발송")
    @PostMapping("/verify/send")
    fun sendVerifyEmail(@RequestBody request: VerifyEmailRequest) {
        subscriptionCommandUseCase.sendVerifyEmail(request)
    }

    @Operation(summary = "구독 요청")
    @PostMapping
    fun subscript(@RequestBody request: SubscriptRequest) {
        subscriptionCommandUseCase.subscript(request)
    }

    @Operation(summary = "이메일별 구독 정보 목록 조회")
    @GetMapping
    fun getSubscriptionsByEmail(
        @RequestParam email: String,
        @RequestParam token: String
    ): ApiResponse<List<SubscriptionResponse>> {
        return ApiResponse.create(subscriptionQueryUseCase.getSubscriptionsByEmail(email, token))
    }

    @Operation(summary = "구독 해제")
    @DeleteMapping("/{id}")
    fun unsubscribe(
        @PathVariable id: UUID,
        @RequestParam email: String,
        @RequestParam token: String
    ) {
        subscriptionCommandUseCase.unsubscribe(id, email, token)
    }
}