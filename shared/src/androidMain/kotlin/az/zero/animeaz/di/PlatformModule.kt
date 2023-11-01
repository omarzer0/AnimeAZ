package az.zero.animeaz.di

import app.cash.sqldelight.db.SqlDriver
import az.zero.animeaz.data.local.database.AnimeDatabaseSourceImpl
import az.zero.animeaz.data.local.database.DBDriverFactory
import az.zero.animeaz.data.local.file_storage.ImageStorageHandler
import az.zero.animeaz.database.AppDatabase
import az.zero.animeaz.domain.database.AnimeDatabaseSource
import az.zero.animeaz.presentation.string_util.StringProvider
import org.koin.core.module.Module
import org.koin.dsl.module

actual class PlatformModule {
    actual val module: Module = module {

        single<SqlDriver> { DBDriverFactory(get()).create() }
        single<AppDatabase> { AppDatabase(get()) }
        single<AnimeDatabaseSource> { AnimeDatabaseSourceImpl(get(),get()) }

        single<StringProvider> { StringProvider(get()) }
        single<ImageStorageHandler> { ImageStorageHandler(get()) }
    }
}