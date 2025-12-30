package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
data class MainResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: List<Articles?>
)

@Serializable
data class Articles(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    @SerialName("urlToImage")
    val urlImage: String?,
    val publishedAt: String?,
    val content: String?
)


@Serializable
data class Source(
    val id: String?,
    val name: String?

)