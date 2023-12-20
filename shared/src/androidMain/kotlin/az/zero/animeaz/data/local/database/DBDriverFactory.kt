package az.zero.animeaz.data.local.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import az.zero.animeaz.data.local.database.DBConstants.DATABASE_NAME
import az.zero.animeaz.database.AppDatabase

actual class DBDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(
            AppDatabase.Schema,
            context,
            DATABASE_NAME
        )
    }
}