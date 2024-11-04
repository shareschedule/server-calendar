package com.schedule.share.calendar.application.port.inbound

import com.schedule.share.calendar.application.service.calendar.vo.CalendarVO

interface CalendarWithEventCommand {

    fun createCalendarWithEvent(param: CalendarVO.Save): Long
}