package az.zero.animeaz.di

import az.zero.animeaz.data.remote.AnimeRemoteService
import az.zero.animeaz.data.remote.AnimeRemoteServiceImpl
import az.zero.animeaz.data.remote.animeKtorHttpClient
import az.zero.animeaz.data.repository.AnimeRepositoryImpl
import az.zero.animeaz.domain.repository.AnimeRepository
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

expect class PlatformModule {
    val module: Module
}

val sharedModule = module {
    single<HttpClient> { animeKtorHttpClient }
    single<AnimeRemoteService> { AnimeRemoteServiceImpl(get()) }
    single<AnimeRepository> { AnimeRepositoryImpl(get()) }
}