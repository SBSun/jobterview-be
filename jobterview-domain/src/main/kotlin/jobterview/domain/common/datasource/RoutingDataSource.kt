package jobterview.domain.common.datasource

import jobterview.domain.common.enums.DataSourceType
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager

/**
 * 현재 트랜잭션이 readOnly인지 여부를 판단하여 적절한 DataSource를 선택하는 RoutingDataSource
 * 읽기 전용이면 READ DB, 그렇지 않으면 WRITE DB를 사용하도록 분기
 */
class RoutingDataSource : AbstractRoutingDataSource() {
    override fun determineCurrentLookupKey(): Any {
        return if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            DataSourceType.READ
        } else {
            DataSourceType.WRITE
        }
    }
}