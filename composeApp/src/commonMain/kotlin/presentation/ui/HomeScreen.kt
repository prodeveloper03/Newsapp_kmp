package presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import domain.model.RequestState
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform.getKoin
import presentation.viewmodel.NewsViewModel
import ui.theme.onPrimaryContainerLight
import ui.theme.surfaceContainerLowestLight
import utils.getBoldFont



class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings = remember { Settings() }

        // Get the ViewModel with Koin parameters
        val viewModel: NewsViewModel = getKoin().get { parametersOf(settings) }
        val news by viewModel.news

        PullRefreshLayout(
            refreshing = news is RequestState.Loading,
            onRefresh = { viewModel.refreshNews() }
        ) {
            Scaffold(
                containerColor = onPrimaryContainerLight,
                topBar = {
                    Text(
                        "Top Headlines",
                        color = surfaceContainerLowestLight,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        modifier = Modifier.padding(top = 90.dp, start = 16.dp),
                        fontFamily = getBoldFont()
                    )
                }
            ) { paddingValues ->

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding()
                        ),
                    contentAlignment = Alignment.TopCenter
                ) {

                    when (news) {
                        is RequestState.Idle, is RequestState.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
                        }

                        is RequestState.Error -> {
                            Text(
                                text = (news as RequestState.Error).message ?: "Failed to load news",
                                color = Color.Red,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        is RequestState.Success -> {
                            val articles = news.getSuccessData()
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(articles) { article ->
                                    article?.let {
                                        NewsItem(it) {
                                            navigator.push(DetailsScreen(it))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


