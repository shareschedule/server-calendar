package com.schedule.share.common.config

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager

class RoutingDataSource: AbstractRoutingDataSource(){

    companion object{
        private val MASTER = "master"
        private val REPLICA = "replica"
    }

    override fun determineCurrentLookupKey(): Any =
        if(TransactionSynchronizationManager.isCurrentTransactionReadOnly())
            REPLICA else MASTER
}