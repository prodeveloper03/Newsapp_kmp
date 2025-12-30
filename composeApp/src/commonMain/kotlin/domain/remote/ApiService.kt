package domain.remote

import domain.model.Articles


interface ApiService {
    suspend fun getTopHeadline(): List<Articles>
}
