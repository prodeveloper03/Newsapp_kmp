package data.repository

import data.cache.NewsCache
import domain.model.Articles
import domain.model.RequestState
import domain.remote.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepository(
    private val apiService: ApiService,
    private val cache: NewsCache
) {

    private val CACHE_TTL = 10 * 60 * 1000L

    fun getTopHeadline(): Flow<RequestState<List<Articles>>> = flow {

        val cachedArticles = cache.load()
        if (cachedArticles != null) {
            emit(RequestState.Success(cachedArticles))
            if (cache.isValid(CACHE_TTL)) return@flow
        }

        emit(RequestState.Loading)

        try {
            val articles = apiService.getTopHeadline()

            println("NewsRepository - fetched articles: $articles")

            cache.save(articles)
            emit(RequestState.Success(articles))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(RequestState.Error(e.message ?: "Failed to load news"))
        }
    }
}
