package com.schedule.share.common.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import javax.sql.DataSource
import kotlin.collections.HashMap

@Configuration
class DataSourceConfig {

    companion object{
        private const val MASTER = "master"
        private const val REPLICA = "replica"
    }

    @Bean
    @Primary
    fun dataSource():DataSource = LazyConnectionDataSourceProxy(
        routingDataSource(masterDataSource(), replicaDataSource())
    )

    @Bean
    @Qualifier(MASTER)
    @ConfigurationProperties(prefix = "spring.datasource.master")
    fun masterDataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean
    @Qualifier(REPLICA)
    @ConfigurationProperties(prefix = "spring.datasource.replica")
    fun replicaDataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean
    fun routingDataSource(
        @Qualifier(MASTER) masterDataSource: DataSource,
        @Qualifier(REPLICA) replicaDataSource: DataSource,
    ): DataSource {
        val routingDataSource = RoutingDataSource()

        val dataSourceMap:HashMap<Any, Any> = HashMap()
        dataSourceMap.put(MASTER, masterDataSource)
        dataSourceMap.put(REPLICA, replicaDataSource)

        routingDataSource.setTargetDataSources(dataSourceMap)
        routingDataSource.setDefaultTargetDataSource(masterDataSource)

        return routingDataSource
    }
}