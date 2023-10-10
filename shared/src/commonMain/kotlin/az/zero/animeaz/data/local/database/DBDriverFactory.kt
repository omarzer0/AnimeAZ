package az.zero.animeaz.data.local.database

import com.squareup.sqldelight.db.SqlDriver

expect class DBDriverFactory {
    fun create(): SqlDriver

}