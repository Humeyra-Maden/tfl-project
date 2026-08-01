package com.asiselectronics.tflappproject.data.repository

import com.asiselectronics.tflappproject.domain.models.RouteStationDomainModel

interface LineRepository {

    suspend fun searchLines(query: String): RouteStationDomainModel

}