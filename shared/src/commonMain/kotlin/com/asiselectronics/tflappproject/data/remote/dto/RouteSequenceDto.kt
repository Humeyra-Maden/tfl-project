package com.asiselectronics.tflappproject.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RouteSequenceResponseDto(
    @SerialName("stations") val stations: List<RouteStationDto> = emptyList()
)

@Serializable
data class RouteStationDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String
)
