package com.asiselectronics.tflappproject.data.remote

import com.asiselectronics.tflappproject.data.remote.dto.ArrivalDto
import com.asiselectronics.tflappproject.data.remote.dto.LineSearchResponseDto
import com.asiselectronics.tflappproject.data.remote.dto.RouteSequenceResponseDto
import com.asiselectronics.tflappproject.data.remote.dto.StopPointSearchResponseDto
import com.asiselectronics.tflappproject.data.remote.dto.TimetableResponseDto
import com.asiselectronics.tflappproject.domain.models.toRouteStationDomainModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class TflApiService(private val client: HttpClient = createHttpClient()){

    private val baseUrl = "https://api.tfl.gov.uk"

    suspend fun searchStopPoints(query : String) : Result<StopPointSearchResponseDto> {
        return try {
            val response: StopPointSearchResponseDto =
                client.get("$baseUrl/StopPoint/Search/$query").body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getArrivals(naptanId: String): Result<List<ArrivalDto>> {
        return try {
            val response: List<ArrivalDto> =
                client.get("$baseUrl/StopPoint/$naptanId/Arrivals").body()
            Result.success(response.sortedBy { it.timeToStationSeconds })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchLines(query: String) : Result<LineSearchResponseDto> {
        return try {
            val response: LineSearchResponseDto =
                client.get("$baseUrl/Line/Search/$query").body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getRouteSequence(lineId: String, direction: String = "outbound"): Result<RouteSequenceResponseDto> {
        return try {
            val response: RouteSequenceResponseDto =
                client.get("$baseUrl/Line/$lineId/Route/Sequence/$direction").body()
            val domainList = response.stations.map{
                it.toRouteStationDomainModel()
            }
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun getTimetable(lineId: String, fromStopId: String): Result<TimetableResponseDto> {
        return try {
            val response: TimetableResponseDto =
                client.get("$baseUrl/Line/$lineId/Timetable/$fromStopId").body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

