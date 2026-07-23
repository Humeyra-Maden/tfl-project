package com.asiselectronics.tflappproject.data.remote

import com.asiselectronics.tflappproject.data.remote.dto.StopPointSearchResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

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

}