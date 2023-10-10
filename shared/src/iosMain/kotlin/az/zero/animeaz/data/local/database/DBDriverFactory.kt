package az.zero.animeaz.data.local.database

import az.zero.animeaz.data.local.database.DBConstants.DATABASE_NAME
import az.zero.animeaz.database.AppDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DBDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(AppDatabase.Schema, DATABASE_NAME)
    }
}