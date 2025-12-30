package data

import domain.model.Articles
import domain.model.MainResponse
import domain.model.RequestState
import domain.remote.ApiService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json


class ApiServiceImpl(
    private val httpClient: HttpClient
) : ApiService {

    override suspend fun getTopHeadline(): List<Articles> {

        val response = httpClient.get(BASE_URL)

        if (response.status.value != 200) {
            throw Exception("HTTP ${response.status}")
        }

        val json = Json { ignoreUnknownKeys = true }
        val apiResponse =
            json.decodeFromString<MainResponse>(response.body())

        return apiResponse.articles.filterNotNull()
    }

    companion object {
        const val BASE_URL =
            "https://newsapi.org/v2/top-headlines?sources=bbc-news"
    }
}
