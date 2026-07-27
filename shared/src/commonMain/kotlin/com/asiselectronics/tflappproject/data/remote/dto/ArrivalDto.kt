package com.asiselectronics.tflappproject.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArrivalDto(
    @SerialName("vehicleId") val vehicleId: String = "",
    @SerialName("lineName") val lineName: String = "",
    @SerialName("destinationName") val destinationName: String = "",
    @SerialName("timeToStation") val timeToStationSeconds: Int = 0
)