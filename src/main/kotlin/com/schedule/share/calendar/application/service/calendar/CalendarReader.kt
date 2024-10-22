package com.schedule.share.calendar.application.service.calendar

import com.schedule.share.calendar.application.port.inbound.CalendarQuery
import com.schedule.share.calendar.application.port.outbound.CalendarQueryPort
import com.schedule.share.calendar.application.service.calendar.vo.CalendarVO
import com.schedule.share.calendar.domain.mapper.toVO
import org.springframework.stereotype.Component

@Component
class CalendarReader(
    private val calendarQueryPort: CalendarQueryPort,
) : CalendarQuery {

    override fun get(
        id: Long,
        UserId: Long
    ): CalendarVO.Calendar = calendarQueryPort.findById(id).toVO()

    override fun list(
        userId: Long
    ): List<CalendarVO.Calendar> = calendarQueryPort.findAllByUserId(userId).map{ it.toVO() }
}