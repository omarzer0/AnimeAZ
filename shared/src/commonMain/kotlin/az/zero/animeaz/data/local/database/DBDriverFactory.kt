package az.zero.animeaz.data.local.database

import app.cash.sqldelight.db.SqlDriver


expect class DBDriverFactory {
    fun create(): SqlDriver

}