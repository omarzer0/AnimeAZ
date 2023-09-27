package az.zero.animeaz.di

import az.zero.animeaz.presentation.string_util.StringProvider
import org.koin.core.module.Module
import org.koin.dsl.module

actual class PlatformModule {
    actual val module: Module = module {
        single<StringProvider> { StringProvider(get()) }
    }
}