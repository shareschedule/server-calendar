package com.schedule.share.calendar.adaptor.inbound.api

import com.schedule.share.calendar.adaptor.inbound.api.dto.CalendarRequestDTO
import com.schedule.share.calendar.adaptor.inbound.api.dto.CalendarResponseDTO
import com.schedule.share.calendar.adaptor.inbound.api.mapper.toResponse
import com.schedule.share.calendar.adaptor.inbound.api.mapper.toVO
import com.schedule.share.calendar.application.port.inbound.CalendarCommand
import com.schedule.share.calendar.application.port.inbound.CalendarQuery
import com.schedule.share.calendar.application.service.calendar.CalendarWithEventService
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/calendars")
class CalendarApi(
    private val calendarQuery: CalendarQuery,
    private val calendarCommand: CalendarCommand,
    private val calendarWithEventService: CalendarWithEventService
) {
    @Operation(summary = "캘린더 단건 조회 API", description = "캘린더 단건 조회 API")
    @GetMapping("/{id}")
    fun get(
        @PathVariable id: Long,
        @RequestHeader("X-UserId") userId: Long
    ): CalendarResponseDTO.Response =
        calendarQuery.get(
            id = id,
            userId = userId
        ).toResponse()

    @Operation(summary = "캘린더 조회 API", description = "캘린더 조회 API")
    @GetMapping
    fun getList(
        @RequestHeader("X-UserId") userId: Long,
        requestParam: HttpServletRequest
    ): List<CalendarResponseDTO.Response> {
        val map = calendarQuery.list(
            userId = userId
        ).map { it.toResponse() }

        return map
    }

    @Operation(summary = "캘린더 등록 API", description = "캘린더 등록 API")
    @PostMapping
    fun post(
        @RequestBody body: CalendarRequestDTO.Calendar,
        @RequestHeader("X-UserId") userId: Long
        //@RequestPart(required = false) image: MultipartFile
    ): Long = calendarWithEventService.createCalendarWithEvent(
        param = body.toVO(
            createdBy = 1L,
            image = byteArrayOf(),
            userId = userId
        )
    )

    // TODO:새로 생성이 됨, 업데이트로 수정하기
    @Operation(summary = "캘린더 수정 API", description = "캘린더 수정 API")
    @PutMapping("/{id}")
    fun put(
        @PathVariable id: Long,
        @RequestBody body: CalendarRequestDTO.Calendar,
        @RequestHeader("X-UserId") userId: Long
        // @RequestPart(required = false) image: MultipartFile
    ) {
        calendarCommand.update(
            id = id,
            param = body.toVO(
                createdBy = 1L,
                image = byteArrayOf(),
                userId = userId
            )
        )
    }

    @Operation(summary = "캘린더 삭제 API", description = "캘린더 삭제 API")
    @DeleteMapping("/{id}")
    fun put(
        @PathVariable id: Long,
    ) {
        calendarCommand.delete(id = id)
    }
}
