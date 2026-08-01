package com.asiselectronics.tflappproject.domain.usecase

import com.asiselectronics.tflappproject.data.repository.LineRepository
import com.asiselectronics.tflappproject.domain.models.RouteStationDomainModel


class GetRouteStationsUseCase(private val repository: LineRepository) {

    // The invoke operator allows the class instance to be called like a function
    suspend operator fun invoke(lineId: String): RouteStationDomainModel {

        return repository.searchLines(lineId)

    }
}