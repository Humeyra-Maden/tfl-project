package com.asiselectronics.tflappproject.domain.models

import com.asiselectronics.tflappproject.data.remote.dto.RouteStationDto


data class RouteStationDomainModel(
    val id: String,
    val name: String
)


fun RouteStationDto.toRouteStationDomainModel(): RouteStationDomainModel {
    return RouteStationDomainModel(
        id = this.id,
        name = this.name
    )
}