package di

import com.russhwolf.settings.Settings
import data.ApiServiceImpl
import data.cache.NewsCache
import data.network.createHttpClient
import data.repository.NewsRepository
import domain.remote.ApiService
import io.ktor.client.HttpClient
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import presentation.viewmodel.NewsViewModel



val appModule = module {

    single { createHttpClient() }

    single<ApiService> { ApiServiceImpl(get()) }

    single { (settings: Settings) -> NewsCache(settings) }

    single { (settings: Settings) -> NewsRepository(get { parametersOf(settings) }, get { parametersOf(settings) }) }

    factory { (settings: Settings) -> NewsViewModel(get { parametersOf(settings) }) }
}





fun initializeKoin() {

    startKoin {
        modules(appModule)
    }

}