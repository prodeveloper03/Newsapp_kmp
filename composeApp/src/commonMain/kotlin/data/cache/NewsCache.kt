package data.cache

import com.russhwolf.settings.Settings
import domain.model.Articles
import kotlinx.datetime.Clock
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NewsCache(
    private val settings: Settings
) {

    private val NEWS_KEY = "cached_news"
    private val TIME_KEY = "cached_news_time"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    fun save(articles: List<Articles>) {
        settings.putString(
            NEWS_KEY,
            json.encodeToString(articles)
        )
        settings.putLong(
            TIME_KEY,
            Clock.System.now().toEpochMilliseconds()
        )
    }

    fun load(): List<Articles>? =
        settings.getStringOrNull(NEWS_KEY)?.let {
            json.decodeFromString(it)
        }


    fun isValid(ttlMillis: Long): Boolean {
        val cachedTime = settings.getLong(TIME_KEY, 0L)
        val now = Clock.System.now().toEpochMilliseconds()
        return now - cachedTime < ttlMillis
    }

    fun clear() {
        settings.remove(NEWS_KEY)
        settings.remove(TIME_KEY)
    }
}
