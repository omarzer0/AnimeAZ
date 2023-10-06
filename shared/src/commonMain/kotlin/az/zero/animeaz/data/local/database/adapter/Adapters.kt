package az.zero.animeaz.data.local.database.adapter

import az.zero.animeaz.domain.model.Genre
import com.squareup.sqldelight.ColumnAdapter
import database.AnimeEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val listOfGenreAdapter = object : ColumnAdapter<List<Genre>, String> {
    override fun decode(databaseValue: String): List<Genre> {
        return if (databaseValue.isEmpty()) {
            listOf()
        } else {
//            databaseValue.split(",").map { Json.decodeFromString(it) }
            Json.decodeFromString(databaseValue)
        }
    }

    override fun encode(value: List<Genre>) = Json.encodeToString(value)


}

val animeEntityAdapter = AnimeEntity.Adapter(listOfGenreAdapter)