package az.zero.animeaz.data.local.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

actual class DBDriverFactory {
    actual fun create(): SqlDriver {
        return JdbcSqliteDriver("jdbc:sqlite:${DBConstants.DATABASE_NAME}")
    }
}