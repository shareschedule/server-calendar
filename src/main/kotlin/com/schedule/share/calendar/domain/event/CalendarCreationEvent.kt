package com.schedule.share.calendar.domain.event

data class CalendarCreationEvent(
    val topic: String,
    val body: CalendarCreationEventBody,
)

data class CalendarCreationEventBody(
    val calendarId: Long,
    val userId: Long,
)