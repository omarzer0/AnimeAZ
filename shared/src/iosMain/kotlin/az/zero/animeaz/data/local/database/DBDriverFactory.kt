package az.zero.animeaz.data.local.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import az.zero.animeaz.data.local.database.DBConstants.DATABASE_NAME
import az.zero.animeaz.database.AppDatabase

actual class DBDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(AppDatabase.Schema, DATABASE_NAME)
    }
}