package com.schedule.share.calendar.adaptor.outbound

import com.schedule.share.calendar.application.port.outbound.CalendarQueryPort
import com.schedule.share.calendar.domain.Calendar
import com.schedule.share.calendar.domain.mapper.toDomain
import com.schedule.share.infra.rdb.repository.CalendarRepository
import org.springframework.stereotype.Component

@Component
class CalendarQueryAdaptor(
    private val calendarRepository: CalendarRepository
) : CalendarQueryPort {
    override fun findAll(): List<Calendar> {
        return calendarRepository.findAllByDeletedAtIsNull()
            .map { it.toDomain() }
    }

    override fun findById(id: Long): Calendar {
        return calendarRepository.findById(id)
            .orElseThrow()
            .toDomain()
    }

    override fun findAllByUserId(userId: Long): List<Calendar> {
        return calendarRepository.findAllByUserId(userId)
            .map { it.toDomain() }
    }
}
