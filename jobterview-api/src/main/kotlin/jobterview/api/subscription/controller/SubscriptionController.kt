package jobterview.api.subscription.controller

import jobterview.api.subscription.request.SubscriptRequest
import jobterview.api.subscription.request.VerifyEmailRequest
import jobterview.api.subscription.service.SubscriptionService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/subscriptions")
class SubscriptionController (
    private val subscriptionService: SubscriptionService
){

    @PostMapping("/verify/send")
    fun sendVerifyEmail(@RequestBody request: VerifyEmailRequest) {
        subscriptionService.sendVerifyEmail(request.email)
    }

    @PostMapping
    fun subscript(@RequestBody request: SubscriptRequest) {
        subscriptionService.subscript(request)
    }
}