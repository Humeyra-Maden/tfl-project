package com.asiselectronics.tflappproject.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StopPointSearchResponseDto(
    @SerialName("matches") val matches : List<StopPointMatchDto> = emptyList()
)

@Serializable
data class StopPointMatchDto(
    @SerialName("id") val id : String,
    @SerialName("name") val name : String,
    @SerialName("lat") val lat : Double = 0.0,
    @SerialName("lon") val lon : Double = 0.0

)