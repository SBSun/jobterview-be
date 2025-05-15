package jobterview.domain.subscription.repository

import jobterview.domain.subscription.Subscription
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SubscriptionJpaRepository : JpaRepository<Subscription, UUID> {

    fun existsByEmailAndJob_Id(email: String, jobId: UUID): Boolean

    @EntityGraph(attributePaths = ["job"])
    fun findAllByEmailOrderByCreatedAtDesc(email: String): List<Subscription>
}