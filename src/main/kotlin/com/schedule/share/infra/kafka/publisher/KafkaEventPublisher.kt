package com.schedule.share.infra.kafka.publisher

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    val objectMapper = ObjectMapper()
    fun publishEvent(topic:String, body:Any) {
        val value = objectMapper.writeValueAsString(body)
        kafkaTemplate.send(topic, value)
    }
}