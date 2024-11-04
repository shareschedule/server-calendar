package com.schedule.share.calendar.application.service.calendar

import com.schedule.share.calendar.application.port.inbound.CalendarWithEventCommand
import com.schedule.share.calendar.application.service.calendar.vo.CalendarVO
import com.schedule.share.calendar.domain.event.CalendarCreationEvent
import com.schedule.share.calendar.domain.event.CalendarCreationEventBody
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class CalendarWithEventService(
    private val calendarWriter: CalendarWriter,
    private val eventPublisher: ApplicationEventPublisher
) : CalendarWithEventCommand{

    private val CREATE_CALENDAR_EVENT = "calendar-create-calendars"

    @TransactionalEventListener
    override fun createCalendarWithEvent(param: CalendarVO.Save): Long {
        val calendarId = calendarWriter.create(param)

        eventPublisher.publishEvent(
            CalendarCreationEvent(
                topic = CREATE_CALENDAR_EVENT,
                body = CalendarCreationEventBody(
                    calendarId = calendarId,
                    userId = param.userId
                )
            )
        )

        return calendarId
    }
}