package com.schedule.share.calendar.application.service

interface DomainQuery<T> {

    fun get(userId: Long, id: Long): T

    fun list(userId: Long): List<T>
}
