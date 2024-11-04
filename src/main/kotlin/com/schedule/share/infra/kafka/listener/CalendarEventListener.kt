package com.schedule.share.infra.kafka.listener

import com.schedule.share.calendar.domain.event.CalendarCreationEvent
import com.schedule.share.infra.kafka.publisher.KafkaEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CalendarEventListener (
    private val publisher: KafkaEventPublisher
) {

    @EventListener
    fun publishCalendarCreationEvent(event: CalendarCreationEvent) {
        publisher.publishEvent(event.topic, event.body )
    }
}