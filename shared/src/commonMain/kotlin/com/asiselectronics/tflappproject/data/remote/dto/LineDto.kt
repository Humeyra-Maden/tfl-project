package com.asiselectronics.tflappproject.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LineSearchResponseDto(
    @SerialName("searchMatches") val matches: List<LineMatcheDto> = emptyList()
)

@Serializable
data class LineMatcheDto(
    @SerialName("lineId") val lineId : String,
    @SerialName("lineName") val lineName : String,
    @SerialName("mode") val mode : String = ""
)