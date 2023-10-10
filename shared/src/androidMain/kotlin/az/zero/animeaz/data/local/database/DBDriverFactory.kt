package az.zero.animeaz.data.local.database

import android.content.Context
import az.zero.animeaz.data.local.database.DBConstants.DATABASE_NAME
import az.zero.animeaz.database.AppDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

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