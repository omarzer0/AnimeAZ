package az.zero.animeaz.data.local.preferences

import com.russhwolf.settings.Settings

object Preferences {
    private val settings = Settings()
    fun getBioAuthLock(): Boolean {
        return settings.getBoolean(BIO_AUTH_KEY, false)
    }

    fun saveBioAuthLock(useLock: Boolean) {
        settings.putBoolean(BIO_AUTH_KEY, useLock)
    }


    private const val BIO_AUTH_KEY = "BIO_AUTH_KEY"

}