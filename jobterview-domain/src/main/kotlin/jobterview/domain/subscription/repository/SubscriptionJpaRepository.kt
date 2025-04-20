package jobterview.domain.subscription.repository

import jobterview.domain.subscription.Subscription
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SubscriptionJpaRepository : JpaRepository<Subscription, UUID> {

    fun findFirstByOrderByCreatedAtDesc(): Subscription?
}