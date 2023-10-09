package az.zero.animeaz.di

import az.zero.animeaz.data.local.database.adapter.animeEntityAdapter
import az.zero.animeaz.data.remote.AnimeRemoteService
import az.zero.animeaz.data.remote.AnimeRemoteServiceImpl
import az.zero.animeaz.data.remote.animeKtorHttpClient
import az.zero.animeaz.data.repository.AnimeRepositoryImpl
import az.zero.animeaz.data.usecase.AnimeFavClickUseCase
import az.zero.animeaz.domain.repository.AnimeRepository
import az.zero.animeaz.util.Constants.GLOBAL_SCOPE
import database.AnimeEntity
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect class PlatformModule {
    val module: Module
}

val sharedModule = module {
    single<HttpClient> { animeKtorHttpClient }
    single<AnimeEntity.Adapter> { animeEntityAdapter }
    single<AnimeRemoteService> { AnimeRemoteServiceImpl(get()) }
    single<AnimeRepository> { AnimeRepositoryImpl(get(), get()) }
    single<CoroutineScope>(named(GLOBAL_SCOPE)) { CoroutineScope(SupervisorJob()) }
    single<AnimeFavClickUseCase> { AnimeFavClickUseCase(get(named(GLOBAL_SCOPE)), get(), get(), get()) }
}