package com.asiselectronics.tflappproject.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimetableResponseDto(
    @SerialName("timetable") val timetable: TimetableDto? = null
)

@Serializable
data class TimetableDto(
    @SerialName("routes") val routes: List<TimetableRouteDto> = emptyList()
)

@Serializable
data class TimetableRouteDto(
    @SerialName("schedules") val schedules: List<ScheduleDto> = emptyList()
)

@Serializable
data class ScheduleDto(
    @SerialName("name") val name: String = "",
    @SerialName("knownJourneys") val knownJourneys: List<KnownJourneyDto> = emptyList()
)

@Serializable
data class KnownJourneyDto(
    @SerialName("hour") val hour: String,
    @SerialName("minute") val minute: String
)