package jobterview.batch.subscription.repository

import jobterview.domain.subscription.Subscription
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SubscriptionRepository : JpaRepository<Subscription, UUID> {
}