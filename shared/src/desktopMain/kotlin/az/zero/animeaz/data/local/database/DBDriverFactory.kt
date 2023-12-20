package az.zero.animeaz.data.local.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

actual class DBDriverFactory {
    actual fun create(): SqlDriver {
        return JdbcSqliteDriver("jdbc:sqlite:${DBConstants.DATABASE_NAME}")
    }
}