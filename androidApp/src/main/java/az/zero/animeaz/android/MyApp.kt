package az.zero.animeaz.android

import android.app.Application
import az.zero.animeaz.di.PlatformModule
import az.zero.animeaz.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            modules(PlatformModule().module + sharedModule)
        }
    }

}