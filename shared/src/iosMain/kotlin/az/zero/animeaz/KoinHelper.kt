package az.zero.animeaz

import az.zero.animeaz.di.PlatformModule
import az.zero.animeaz.di.sharedModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(PlatformModule().module, sharedModule)
    }
}