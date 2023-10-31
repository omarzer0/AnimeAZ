package az.zero.animeaz.data.local.database


import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.sqljs.Database
import com.squareup.sqldelight.drivers.sqljs.JsSqlDriver

actual class DBDriverFactory {
    actual fun create(): SqlDriver {
        return JsSqlDriver(Database())
    }
}
