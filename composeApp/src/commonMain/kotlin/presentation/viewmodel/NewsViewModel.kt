package presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.repository.NewsRepository
import domain.model.Articles
import domain.model.RequestState
import domain.remote.ApiService
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch



class NewsViewModel(
    private val repository: NewsRepository
) : ScreenModel {

    private val _news =
        mutableStateOf<RequestState<List<Articles>>>(RequestState.Idle)
    val news: State<RequestState<List<Articles>>> = _news

    init {
        loadNews()
    }

    private fun loadNews() {
        screenModelScope.launch {
            repository.getTopHeadline().collectLatest {
                _news.value = it
            }
        }
    }

    fun refreshNews() {
        loadNews()
    }
}

