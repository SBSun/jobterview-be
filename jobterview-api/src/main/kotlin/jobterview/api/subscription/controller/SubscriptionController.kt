package jobterview.api.subscription.controller

import jobterview.api.subscription.request.VerifyEmailRequest
import jobterview.api.subscription.service.SubscriptionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    fun sendVerifyEmail(@RequestBody request: VerifyEmailRequest): ResponseEntity<Void> {
        subscriptionService.sendVerifyEmail(request.email)

        return ResponseEntity.status(HttpStatus.OK).build()
    }
}