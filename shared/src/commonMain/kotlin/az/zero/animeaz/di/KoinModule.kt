package az.zero.animeaz.di

import org.koin.core.module.Module
import org.koin.dsl.module

expect class PlatformModule {
    val module: Module
}

val sharedModule = module {

}